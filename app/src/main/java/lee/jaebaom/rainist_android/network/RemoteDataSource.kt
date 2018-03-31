package lee.jaebaom.rainist_android.network

import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Flowable
import io.reactivex.Observable
import lee.jaebaom.rainist_android.BuildConfig

import lee.jaebaom.rainist_android.data.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
object RemoteDataSource {
    val baseURL: String = "https://api.github.com"
    private val remote: Remote = createRetrofit(Remote::class.java, baseURL)

    fun repoDataAsync(userName: String?, page: Int): Call<List<Repository>> =
            remote.userRepoData(userName, page)

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
                readTimeout(2000, TimeUnit.MILLISECONDS)
                writeTimeout(2000, TimeUnit.MILLISECONDS)
                connectTimeout(2000, TimeUnit.MILLISECONDS)
                addInterceptor(initHttpLoggingInterceptor())
            }.build()

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