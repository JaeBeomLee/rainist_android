package lee.jaebaom.rainist_android.network

import io.reactivex.Flowable
import io.reactivex.Observable
import lee.jaebaom.rainist_android.data.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
interface Remote {
    //특정 유저의 repositories를 찾아보는 function
    @GET("/users/{user_name}/repos")
    fun userRepoData(
        @Path("user_name") name: String?,
        @Query("page") page: Int
    ): Call<List<Repository>>

    //특정 repository의 star을 클릭해준 Star Gazers를 찾아보는 function
    @GET("/repos/{user_name}/{repo_name}/stargazers")
    fun starGazersData(
            @Path("user_name") name: String,
            @Path("repo_name") repoName: String,
            @Query("page") page: Int
    )
    // result: ArrayList<User>

    //특정 유저의 star을 달아준 repositories를 찾아주는 function
    @GET("/users/{user_name}/starred")
    fun userStarredData(
            @Path("user_name") name: String,
            @Query("page") page: Int
    )
    // result: ArrayList<Repository>
}