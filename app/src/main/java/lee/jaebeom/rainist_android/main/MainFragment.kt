package lee.jaebeom.rainist_android.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.frament_main.*
import lee.jaebeom.rainist_android.R
import lee.jaebeom.rainist_android.data.Repository
import lee.jaebeom.rainist_android.data.User
import lee.jaebeom.rainist_android.util.glideLoad

/**
 * Created by leejaebeom on 2018. 3. 30..
 * 이름과 avatar 썸네일이 나올 fragment 입니다.
 */
class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frament_main, container, false)
    }

    fun userDataChange(user: User?){
        fragment_name.text = user?.login
        context?.let { glideLoad(it, user?.avatar_url, fragment_avatar) }
    }

    fun userDataChange(repository: Repository?){
        fragment_name.text = repository?.name
        context?.let { glideLoad(it, repository?.owner?.avatar_url, fragment_avatar) }
    }
}