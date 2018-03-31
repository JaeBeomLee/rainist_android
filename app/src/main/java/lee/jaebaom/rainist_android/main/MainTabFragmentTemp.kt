package lee.jaebaom.rainist_android.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recycler.*
import kotlinx.android.synthetic.main.fragment_recycler.view.*
import lee.jaebaom.rainist_android.R
import lee.jaebaom.rainist_android.data.Data
import lee.jaebaom.rainist_android.data.Repository
import lee.jaebaom.rainist_android.network.RemoteDataSource
import lee.jaebaom.rainist_android.util.PageLink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class MainTabFragmentTemp : Fragment() {
    var page: Int = 1
    var total : Int? = null
    companion object {
        fun newInstance(): MainTabFragmentTemp{
            val args = Bundle()
            val fragment = MainTabFragmentTemp()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recycler, container, false)
//        view.recycler.adapter = MainRecyclerAdapter()
//        val remote = RemoteDataSource.repoDataAsync(getString(R.string.default_user), page)
//        remote.enqueue(object : Callback<List<Repository>>{
//            override fun onFailure(call: Call<List<Repository>>?, t: Throwable?) {}
//
//            override fun onResponse(call: Call<List<Repository>>?, response: Response<List<Repository>>?) {
//                Data.repositories.addAll(ArrayList(response?.body()))
//                (view.recycler.adapter as MainRecyclerAdapter).notifyDataSetChanged()
//                val data = response?.headers()?.get("Link")
//                total = PageLink().getLast(data!!)
//                Log.d("head link", data)
//            }
//        })
//
//        initPagenation(view)

        return view
    }

    fun initPagenation(view: View){
        view.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val VISIBLE_THRESHOLD = 1
                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                var loading = false
                if (totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)  && page < total!!){
                    //input
                    page++
                    val remote = RemoteDataSource.repoDataAsync(getString(R.string.default_user), page)
                    remote.enqueue(object : Callback<List<Repository>>{
                        override fun onFailure(call: Call<List<Repository>>?, t: Throwable?) {}

                        override fun onResponse(call: Call<List<Repository>>?, response: Response<List<Repository>>?) {
                            Data.repositories.addAll(ArrayList(response?.body()))
                            (view.recycler.adapter as MainRecyclerAdapter).notifyDataSetChanged()
                            val data = response?.headers()?.get("Link")
                            total = PageLink().getLast(data!!)
                            Log.d("head link", data)
                        }
                    })
                }

            }
        })
    }
}