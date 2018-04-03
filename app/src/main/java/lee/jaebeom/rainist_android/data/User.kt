package lee.jaebeom.rainist_android.data

import java.io.Serializable

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
data class User (
        val login: String,
        val id: Long,
        val avatar_url: String, //user 이미지 url
        val url: String, //user github url
        var starred : ArrayList<Repository> //userStarredData api를 사용해서 저장해야함
) : Serializable