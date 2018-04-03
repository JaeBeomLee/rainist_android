package lee.jaebeom.rainist_android.data

import java.io.Serializable

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
data class Repository(
        val name: String,
        val description: String,
        val stargazers_count: Int,  //50이 넘으면 빨간색
        val owner: User, //소유자 정보
        var starGazers: ArrayList<User>? //starGazersData api 사용해서 저장해야함
): Serializable