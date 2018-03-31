package lee.jaebaom.rainist_android.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_recycler.view.*
import lee.jaebaom.rainist_android.R
import lee.jaebaom.rainist_android.data.Data
import lee.jaebaom.rainist_android.data.Repository
import lee.jaebaom.rainist_android.network.RemoteDataSource
import lee.jaebaom.rainist_android.util.PageLink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class MainTabFragment : Fragment(), MainContract.TabView{
    var page: Int = 1
    var total : Int? = null
    var loading = false
    lateinit var presenter: RecyclerPresenter

    companion object {
        fun newInstance(): MainTabFragment{
            val args = Bundle()
            val fragment = MainTabFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recycler, container, false)
        view.recycler.adapter = MainRecyclerAdapter()
        presenter = RecyclerPresenter(view.recycler.adapter as MainRecyclerAdapter, this)
        presenter.getInitData(context, page)
        initPagenation(view)

        return view
    }

    override fun initTotal(data: String) {
        total = PageLink().getLast(data)
    }

    private fun initPagenation(view: View){
        view.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val VISIBLE_THRESHOLD = 1
                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)  && page < total!!){
                    //input
                    loading = true
                    page++
                    presenter.getData(context, page)
                }

            }
        })
    }

    override fun isLoading() {
        loading = !loading
    }

}