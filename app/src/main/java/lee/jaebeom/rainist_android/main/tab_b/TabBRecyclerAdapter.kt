package lee.jaebeom.rainist_android.main.tab_b

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable

import kotlinx.android.synthetic.main.item_main.view.*
import kotlinx.android.synthetic.main.item_main_recycler.view.*
import lee.jaebeom.rainist_android.R
import lee.jaebeom.rainist_android.data.Data
import lee.jaebeom.rainist_android.data.Data.Companion.starGazers
import lee.jaebeom.rainist_android.data.Repository
import lee.jaebeom.rainist_android.data.User
import lee.jaebeom.rainist_android.main.MainContract
import lee.jaebeom.rainist_android.main.MainFragment

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class TabBRecyclerAdapter(private val context: Context, private val fragment: MainFragment) : RecyclerView.Adapter<TabBRecyclerAdapter.BaseViewHolder>(){
    private val ITEM_VIEW = 0
    private val RECYCLER_VIEW = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == ITEM_VIEW){
            ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false), fragment)
        }else{
            RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_recycler, parent, false), fragment)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val itemPosition = position / 2 //item 두개 당 하나의 repository를 쓰기 때문에 2로 나눠야 원래 repository가 나온다.
        holder.bind(starGazers[itemPosition], itemPosition)
    }

    override fun getItemCount(): Int = starGazers.size * 2

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0){
            ITEM_VIEW   //짝수
        }else {
            RECYCLER_VIEW   //홀수
        }
    }

    abstract class BaseViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        abstract fun bind(starGazer: User, position: Int)
    }

    class ItemViewHolder(itemView: View, private val fragment: MainFragment) : BaseViewHolder(itemView){
        override fun bind(starGazer: User, position: Int) {
            itemView.setOnClickListener {
                fragment.userDataChange(starGazer)
            }
            itemView.name.text = starGazer.login
            itemView.sub_text.text = starGazer.url //?: "URL이 없습니다."
        }

    }

    class RecyclerViewHolder(private val view: View, private val fragment: MainFragment) : BaseViewHolder(view), MainContract.ViewHoloerView {
        var loading: Boolean = false
//        var presenter : InsideRecyclerPresenter? = null
//        private var starred: ArrayList<Repository>? = null

        override fun bind(starGazer: User, position: Int) {
            val adapter = ItemRecyclerAdapter(starGazer.starred, fragment)
            view.recycler.adapter = adapter
//            starred = ArrayList()
//            val adapter = ItemRecyclerAdapter(starred)
//            Observable.fromIterable(starGazer.starred)
//                    .take(20 * starGazer.starredCurrentPage.toLong())
//                    .subscribe {
//                        starred?.add(it)
//                    }
//            presenter = InsideRecyclerPresenter(adapter, this)
//            presenter?.initTotal(starGazer)
//            presenter?.initCurrent(starGazer)
//            initPagination(view, position)
        }

        @Deprecated("Pagination 미사용으로 사용하지 않음.")
        private fun initPagination(view: View, position: Int){
            view.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val VISIBLE_THRESHOLD = 1
                    val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

//                    if (!loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)  && Data.starGazers[position].starredCurrentPage < Data.starGazers[position].starredTotalPage){
//                        view.progressBar.visibility = View.VISIBLE
//                        //input
//                        isLoading() //loading = true
////                        starred?.let { presenter?.getStarredData(Data.starGazers[position], it) }
//                    }

                }
            })
        }

        override fun hideProgressBar() {
            view.progressBar.visibility = View.GONE
        }
        override fun isLoading(){
            loading != loading
        }
    }
}