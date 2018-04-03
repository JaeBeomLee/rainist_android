package lee.jaebeom.rainist_android.network

import io.reactivex.Observable
import lee.jaebeom.rainist_android.data.Repository
import lee.jaebeom.rainist_android.data.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
interface Remote {
    //특정 유저의 repositories를 찾아보는 function
    @GET("/users/{user_name}/repos?per_page=100")
    fun userRepoData(
        @Path("user_name") name: String?,
        @Query("access_token") token: String?,
        @Query("page") page: Int
    ): Observable<Response<List<Repository>>>

    //특정 repository의 star을 클릭해준 Star Gazers를 찾아보는 function
    @GET("/repos/{user_name}/{repo_name}/stargazers?per_page=100")
    fun starGazersData(
            @Path("user_name") name: String?,
            @Path("repo_name") repoName: String?,
            @Query("access_token") token: String?,
            @Query("page") page: Int
    ): Observable<Response<List<User>>>

}