package lee.jaebeom.rainist_android.main

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import lee.jaebeom.rainist_android.R
import lee.jaebeom.rainist_android.util.isNetworking
import lee.jaebeom.rainist_android.util.simpleAlertDialog

class MainActivity : FragmentActivity(), MainContract.View {
    lateinit var presenter: MainPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPresenter()

        if (isNetworking(this)){
            presenter.initReposData(this)
        }else{
            simpleAlertDialog(this, R.string.default_dialog_no_network, "확인해 볼게요")
        }

//        테스트 용 코드
//        isEnd()
//        val starGazers: ArrayList<User> = ArrayList()
//        val repositories: ArrayList<Repository> = ArrayList()
//        repositories.add(Repository("account-transfer-api", "Sample app demonstrating use of AccountTransferApi ", 9, User("googlesamples", 7378196, "https://avatars3.githubusercontent.com/u/7378196?v=4", "https://api.github.com/users/googlesamples", ArrayList<Repository>()), starGazers))
//        starGazers.add(User("MaTriXy", 3080139, "https://avatars1.githubusercontent.com/u/3080139?v=4", "https://api.github.com/users/MaTriXy", repositories))
//        starGazers.add(User("nisd93", 16471446, "https://avatars0.githubusercontent.com/u/16471446?v=4", "https://api.github.com/users/nisd93", repositories))
//        starGazers.add(User("grapelove", 18024281, "https://avatars0.githubusercontent.com/u/18024281?v=4", "https://api.github.com/users/grapelove", repositories))
//        Data.repositories.addAll(repositories)
//        Data.starGazers.addAll(starGazers)

    }

    override fun initPresenter() {
        presenter = MainPresenter(this)

    }

    override fun isEnd() {
        val adapter = MainPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        presenter.disposables.clear()
        // 매번 테스트 시간이 너무 길어져 저장해서 사용 하려 했지만 StackOverFlow에러로 일단 보류
//        SavaPreference.saveSharedPreference(this, getString(R.string.key_repository), gson.toJson(Data.repositories))
//        SavaPreference.saveSharedPreference(this, getString(R.string.key_star_gazer), gson.toJson(Data.starGazers))
        tab.setupWithViewPager(viewPager)
        progress_layout.visibility = View.GONE

    }

    override fun changeProgress(message: String) {
        progress_text.text = message
    }
}
