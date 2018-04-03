package lee.jaebeom.rainist_android.main.tab_a

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_inside.view.*
import lee.jaebeom.rainist_android.R
import lee.jaebeom.rainist_android.data.User
import lee.jaebeom.rainist_android.main.MainContract
import lee.jaebeom.rainist_android.main.MainFragment
import lee.jaebeom.rainist_android.util.glideLoad

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class ItemRecyclerAdapter(private val starGazers: ArrayList<User>?, private val fragment: MainFragment) : RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder>(), MainContract.InsideRecyclerView{
    override fun notifyListDatachange() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_inside, parent, false), fragment)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(starGazers?.get(position))
    }

    override fun getItemCount(): Int = starGazers?.size!!

    class ItemViewHolder(itemView: View, private val fragment: MainFragment) : RecyclerView.ViewHolder(itemView){
        fun bind(starGazer: User?) {
            itemView.setOnClickListener {
                fragment.userDataChange(starGazer)
            }
            itemView.name.text = starGazer?.login
            glideLoad(itemView, starGazer?.avatar_url, itemView.avatar)
        }

    }
}