package lee.jaebaom.rainist_android.main

import android.content.Context
import kotlinx.android.synthetic.main.fragment_recycler.view.*
import lee.jaebaom.rainist_android.R
import lee.jaebaom.rainist_android.data.Data
import lee.jaebaom.rainist_android.data.Repository
import lee.jaebaom.rainist_android.network.RemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class RecyclerPresenter(private val recyclerView: MainContract.RecyclerView, private val tabView: MainContract.TabView) : MainContract.RecyclerPresenter{
    init {
        recyclerView.initPresenter(this)
    }

    override fun getInitData(context: Context?, page: Int) {
        val remote = RemoteDataSource.repoDataAsync(context?.getString(R.string.default_user), page)
        remote.enqueue(object : Callback<List<Repository>> {
            override fun onFailure(call: Call<List<Repository>>?, t: Throwable?) {}

            override fun onResponse(call: Call<List<Repository>>?, response: Response<List<Repository>>?) {
                Data.repositories.addAll(ArrayList(response?.body()))
                recyclerView.notifyListDatachange()
                val data = response?.headers()?.get("Link")
                tabView.initTotal(data!!)
            }
        })
    }

    override fun getData(context: Context?, page: Int) {
        val remote = RemoteDataSource.repoDataAsync(context?.getString(R.string.default_user), page)
        remote.enqueue(object : Callback<List<Repository>> {
            override fun onFailure(call: Call<List<Repository>>?, t: Throwable?) {}

            override fun onResponse(call: Call<List<Repository>>?, response: Response<List<Repository>>?) {
                Data.repositories.addAll(ArrayList(response?.body()))
                recyclerView.notifyListDatachange()
                tabView.isLoading()

            }
        })
    }

}