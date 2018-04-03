package lee.jaebeom.rainist_android.main

import android.content.Context
import lee.jaebeom.rainist_android.data.Repository
import lee.jaebeom.rainist_android.data.User

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
interface MainContract {
    interface View {
        fun initPresenter()
        fun isEnd()
        fun changeProgress(message: String)
    }

    interface Presenter {
        fun initReposData(context: Context)
        fun initStarGagzerData(context: Context)
        fun getRepoPageData(context: Context) : Int
        fun getRepositoriesData(context: Context, page: Int)
        fun getGazersPageData(context: Context, repos: String) : Int
        fun getGazersData(context: Context, repository: Repository, page: Int)
    }


    //RecyclerView 안에 RecyclerView가 있다는 특성으로 기존 View를 그대로 쓰기엔 안쓰는 function들이 존재해 따로 만듬
    @Deprecated("Pagination 미사용으로 사용하지 않음.")
    interface ViewHoloerView{
        fun hideProgressBar()
        fun isLoading()
    }
    interface InsideRecyclerView{
        fun notifyListDatachange()
    }

    interface TabAInsideRecyclerPresenter{
        fun notifyListDataChange()
        fun getStarGazersData(repository: Repository?, starGazers: ArrayList<User>)
        fun initTotal(repository: Repository?)
        fun initCurrent(repository: Repository?)
    }


    interface TabBInsideRecyclerPresenter{
        fun notifyListDataChange()
        fun getStarredData(starGazer: User, starred: ArrayList<Repository>)
        fun initTotal(starGazer: User)
        fun initCurrent(starGazer: User)
    }
}