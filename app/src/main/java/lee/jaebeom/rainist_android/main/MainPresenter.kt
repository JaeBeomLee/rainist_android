package lee.jaebeom.rainist_android.main

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import lee.jaebeom.rainist_android.R
import lee.jaebeom.rainist_android.data.Data
import lee.jaebeom.rainist_android.data.Error
import lee.jaebeom.rainist_android.data.Repository
import lee.jaebeom.rainist_android.network.RemoteDataSource
import lee.jaebeom.rainist_android.util.getLast
import java.util.*

/**
 * Created by leejaebeom on 2018. 4. 1..
 */
class MainPresenter(private val view: MainContract.View): MainContract.Presenter {
    val gson = Gson()
    val disposables by lazy {
        CompositeDisposable()
    }
    //전체 레포를 가져오는 function
    override fun initReposData(context: Context) {
        val totalRepoPage = getRepoPageData(context)
        Observable.fromIterable(1..totalRepoPage)
                .subscribeOn(Schedulers.io())
                .concatMap {
                    RemoteDataSource.repoDataAsync(context.getString(R.string.default_user), it, context.getString(R.string.access_token))
                }
                .subscribe ({
                    if (it.body() == null){
                        throw Exception(it.errorBody()?.string())
                    }else{
                        Data.repositories.addAll(ArrayList(it.body()))
                    }
                },
                {
                    getErrorMessage(it.message)
                }, {
                    initStarGagzerData(context)
                })
                .apply { disposables.add(this) }
    }

    //전체 StarGazers를 가져오는 function
    override fun initStarGagzerData(context: Context) {
        Observable
                .fromIterable(Data.repositories)
                .flatMap{
                    it.starGazers = ArrayList() //초기화
                    val totalGazersPage = getGazersPageData(context, it.name)
                    Observable.just(it, totalGazersPage)
                }
                .buffer(2)
                .map {
                    val repository = it[0] as Repository
                    Observable.fromIterable(1..it[1] as Int)
                            .subscribe {
                                getGazersData(context, repository, it)
                            }
                    it[0] as Repository

                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    view.changeProgress("${it.name} 완료")
                },{},
                        {
                            view.isEnd()
                        })
                .apply { disposables.add(this) }
    }

    //레포지토리 전체 페이지 추출 function
    override fun getRepoPageData(context: Context): Int {
        val remote = RemoteDataSource.repoDataAsync(context.getString(R.string.default_user), 1, context.getString(R.string.access_token))
        return remote
                .map {
                    getLast(it.headers()?.get("Link"))
                }
                .subscribeOn(Schedulers.io())
                .blockingSingle()
                ?: 1    //예외처리
    }

 ㅁ
    //실질적인 레포 데이터를 가져오는 function
    @Deprecated("순서의 문제로 initReposData function으로 통합함")
    override fun getRepositoriesData(context: Context, page: Int) {
        val remote = RemoteDataSource.repoDataAsync(context.getString(R.string.default_user), page, context.getString(R.string.access_token))
        remote.subscribeOn(Schedulers.io())
                .map {
                    if (it.body() == null){
                        throw Exception(it.errorBody()?.string())
                    }else{
                        it.body()
                    }
                }
                .doOnError {
                    getErrorMessage(it.message)
                }
                .subscribe {
                    Data.repositories.addAll(ArrayList(it))
                }
                .apply { disposables.add(this) }
    }

    override fun getGazersPageData(context: Context, repos: String) : Int{
        val remote = RemoteDataSource.gazerDataAsync(context.getString(R.string.default_user), repos, 1, context.getString(R.string.access_token))
        return remote
                .map {
                    getLast(it.headers()?.get("Link"))
                }
                .subscribeOn(Schedulers.io())
                .blockingSingle()
                ?: 1    //예외처리. 30명 이하의 경우 Link Header가 존재 하지 않아 null이 될 수 있음.

    }

    override fun getGazersData(context: Context, repository: Repository, page: Int) {
        val remote = RemoteDataSource.gazerDataAsync(context.getString(R.string.default_user), repository.name, page, context.getString(R.string.access_token))
        remote.subscribeOn(Schedulers.io())
                .map {
                    if (it.body() == null){
                        throw Exception(it.errorBody()?.string())
                    }else{
                        it.body()
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                            repository.starGazers?.addAll(ArrayList(it))
                            Observable.fromIterable(it)
                                    .subscribe {
                                        Data.binarySearchAdd(it, repository)
                                    }
                        },
                        {
                            getErrorMessage(it.message)
                        }
                )
                .apply { disposables.add(this) }
    }

    private fun getErrorMessage(error: String?){
        var errorObj: Error? = null
        errorObj = try {
            gson.fromJson<Error>(error, Error::class.java)
        }catch (e: JsonSyntaxException){
            gson.fromJson("{\"message\":\"인터넷 연결이 도중에 끊어졌습니다.\"}", Error::class.java)
        }
        Observable.just(error)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.changeProgress(errorObj.message)
                }
    }
}