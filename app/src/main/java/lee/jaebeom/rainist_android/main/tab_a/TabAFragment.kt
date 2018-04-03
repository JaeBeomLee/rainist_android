package lee.jaebeom.rainist_android.main.tab_a

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_recycler.*
import kotlinx.android.synthetic.main.fragment_recycler.view.*
import lee.jaebeom.rainist_android.R
import lee.jaebeom.rainist_android.main.MainFragment

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class TabAFragment : Fragment(){
    companion object {
        fun newInstance(): TabAFragment {
            val args = Bundle()
            val fragment = TabAFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.fragmentA) as MainFragment
        val view = inflater.inflate(R.layout.fragment_recycler, container, false)
        view.recycler.adapter = TabARecyclerAdapter(context!!, fragment)
        view.recycler.adapter.notifyDataSetChanged()
        return view
    }
}