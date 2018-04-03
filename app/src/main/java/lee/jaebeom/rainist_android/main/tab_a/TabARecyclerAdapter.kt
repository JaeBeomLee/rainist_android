package lee.jaebeom.rainist_android.main.tab_a

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.item_main.view.*
import kotlinx.android.synthetic.main.item_main_recycler.view.*
import lee.jaebeom.rainist_android.R
import lee.jaebeom.rainist_android.data.Data.Companion.repositories
import lee.jaebeom.rainist_android.data.Repository
import lee.jaebeom.rainist_android.main.MainContract
import lee.jaebeom.rainist_android.main.MainFragment

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class TabARecyclerAdapter(private val context: Context, private val fragment: MainFragment) : RecyclerView.Adapter<TabARecyclerAdapter.BaseViewHolder>(){
    private val ITEM_VIEW = 0
    private val RECYCLER_VIEW = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == ITEM_VIEW){
            ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false), context, fragment)
        }else{
            RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_recycler, parent, false), fragment)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val itemPosition = position / 2 //item 두개 당 하나의 repository를 쓰기 때문에 2로 나눠야 원래 repository가 나온다.
        holder.bind(repositories[itemPosition], itemPosition)
    }

    override fun getItemCount(): Int = repositories.size * 2

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0){
            ITEM_VIEW   //짝수
        }else {
            RECYCLER_VIEW   //홀수
        }
    }

    abstract class BaseViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        abstract fun bind(repository: Repository?, position: Int)
    }

    class ItemViewHolder(itemView: View, private val context: Context, private val fragment: MainFragment) : BaseViewHolder(itemView){
        override fun bind(repository: Repository?, position: Int) {
            itemView.setOnClickListener{
                fragment.userDataChange(repository)
            }
            itemView.name.text = repository?.name
            itemView.sub_text.text = repository?.description ?: "설명이 없습니다."
            itemView.count.text = repository?.stargazers_count.toString()
            if(repository?.stargazers_count!! >= 50){
                itemView.count.setTextColor(Color.RED)
            }else{
                itemView.count.setTextColor(ContextCompat.getColor(context, R.color.textSecondary))
            }
        }


    }

    class RecyclerViewHolder(private val view: View, private val fragment: MainFragment) : BaseViewHolder(view), MainContract.ViewHoloerView {
//        var presenter : InsideRecyclerPresenter? = null
//        var loading: Boolean = false
//        private var starGazers: ArrayList<User>? = null
        override fun bind(repository: Repository?, position: Int) {
            val adapter = ItemRecyclerAdapter(repository?.starGazers, fragment)
            view.recycler.adapter = adapter
//            starGazers = ArrayList()
//            val adapter = ItemRecyclerAdapter(starGazers)
//            Observable.fromIterable(repository?.starGazers)
//                    .take(20 * repository?.starGazersCurrentPage?.toLong()!!)
//                    .subscribe {
//                        starGazers?.add(it)
//                    }
//            presenter = InsideRecyclerPresenter(adapter, this)
//            presenter?.initTotal(repository)
//            presenter?.initCurrent(repository)
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

//                    if (!loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)  && Data.repositories[position].starGazersCurrentPage < Data.repositories[position].starGazersTotalPage!!){
//                        view.progressBar.visibility = View.VISIBLE
//                        //input
//                        isLoading() //loading = true
//                        starGazers?.let { presenter?.getStarGazersData(Data.repositories[position], it) }
//                    }

                }
            })
        }

        override fun hideProgressBar() {
            view.progressBar.visibility = View.GONE
        }
        override fun isLoading(){
//            loading != loading
        }
    }
}