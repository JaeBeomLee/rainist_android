package lee.jaebeom.rainist_android.main.tab_b

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_recycler.view.*
import lee.jaebeom.rainist_android.R
import lee.jaebeom.rainist_android.main.MainFragment

/**
 * Created by leejaebeom on 2018. 3. 30..
 * B탭의 fragment 입니다.
 */
class TabBFragment : Fragment(){
    companion object {
        fun newInstance(): TabBFragment {
            val args = Bundle()
            val fragment = TabBFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.fragmentA) as MainFragment
        val view = inflater.inflate(R.layout.fragment_recycler, container, false)
        view.recycler.adapter = TabBRecyclerAdapter(context!!, fragment)
        return view
    }
}