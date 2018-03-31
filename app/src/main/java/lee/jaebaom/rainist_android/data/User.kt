package lee.jaebaom.rainist_android.data

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
data class User (
        val login: String,
        val avatar_url: String, //user 이미지 url
        val url: String, //user github url
        val starred : ArrayList<Repository> //userStarredData api를 사용해서 저장해야함


)