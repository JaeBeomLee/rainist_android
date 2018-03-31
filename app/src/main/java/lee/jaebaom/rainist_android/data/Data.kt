package lee.jaebaom.rainist_android.data

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class Data {
    companion object {
        val repositories : ArrayList<Repository> = ArrayList()
        val StarGazers : ArrayList<User> = ArrayList()

        //TODO StarGazer정보 받아오면 이진검색으로 하나씩 넣고 없으면 생성
    }
}