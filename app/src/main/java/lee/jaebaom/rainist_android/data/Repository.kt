package lee.jaebaom.rainist_android.data

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
data class Repository (
        val name: String,
        val description: String,
        val stargazers_count: Int,  //50이 넘으면 빨간색
        val owner: User, //소유자 보
        val starGazers: ArrayList<User> //starGazersData api 사용해서 저장해야함
)