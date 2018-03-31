package lee.jaebaom.rainist_android.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_main.view.*
import lee.jaebaom.rainist_android.R
import lee.jaebaom.rainist_android.data.Data
import lee.jaebaom.rainist_android.data.Data.Companion.repositories
import lee.jaebaom.rainist_android.data.Repository

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.BaseViewHolder>(), MainContract.RecyclerView{
    private val ITEM_VIEW = 0
    private val RECYCLER_VIEW = 1

    lateinit var presenter: MainContract.RecyclerPresenter

    override fun initPresenter(presenter: MainContract.RecyclerPresenter) {
        this.presenter = presenter
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(repositories?.get(position))
    }

    override fun getItemCount(): Int = repositories?.size!!

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0){
            ITEM_VIEW   //짝수
        }else {
            RECYCLER_VIEW   //홀수
        }
    }

    override fun notifyListDatachange() {
        notifyDataSetChanged()
    }

    abstract class BaseViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        abstract fun bind(repository: Repository?)
    }

    class ItemViewHolder(itemView: View) : BaseViewHolder(itemView){
        override fun bind(repository: Repository?) {
            itemView.name.text = repository?.name
            itemView.sub_text.text = repository?.description
            itemView.count.text = repository?.stargazers_count.toString()
        }

    }

    class RecyclerViewHolder(itemView: View) : BaseViewHolder(itemView){
        override fun bind(repository: Repository?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}