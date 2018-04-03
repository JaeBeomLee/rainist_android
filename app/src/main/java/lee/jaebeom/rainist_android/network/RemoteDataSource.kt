package lee.jaebeom.rainist_android.network

import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import lee.jaebeom.rainist_android.BuildConfig

import lee.jaebeom.rainist_android.data.Repository
import lee.jaebeom.rainist_android.data.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
object RemoteDataSource {
    val baseURL: String = "https://api.github.com"
    private val remote: Remote = createRetrofit(Remote::class.java, baseURL)

    fun repoDataAsync(userName: String?, page: Int, token: String?): Observable<Response<List<Repository>>> =
            remote.userRepoData(userName, token, page)

    fun gazerDataAsync(userName: String?, repoName: String, page: Int, token: String?): Observable<Response<List<User>>> =
            remote.starGazersData(userName, repoName, token, page)
    fun <T> createRetrofit(clazz: Class<T>, baseURL: String) =
            Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(createOkHttpClient())
                    .build()
                    .create(clazz)

    private fun createOkHttpClient() : OkHttpClient =
            OkHttpClient.Builder().apply {
                readTimeout(2, TimeUnit.MINUTES)
                writeTimeout(2, TimeUnit.MINUTES)
                connectTimeout(2, TimeUnit.MINUTES)
//                addInterceptor(initHttpLoggingInterceptor())
            }.build()

    //천여번의 요청으로 인해 로그 과부하로 Android Studio에 무리가 감.
    private fun initHttpLoggingInterceptor() =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.v("OkHttp", "message :  $it")
            }).apply {
                level = if (BuildConfig.DEBUG){
                    HttpLoggingInterceptor.Level.BODY
                }else{
                    HttpLoggingInterceptor.Level.NONE
                }
            }

}