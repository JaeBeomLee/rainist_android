package lee.jaebeom.rainist_android.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import lee.jaebeom.rainist_android.main.tab_a.TabAFragment
import lee.jaebeom.rainist_android.main.tab_b.TabBFragment

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class MainPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager){

    override fun getPageTitle(position: Int): CharSequence? = arrayOf("A", "B")[position]

    override fun getItem(position: Int): Fragment {
        return if (position == 0){
            TabAFragment.newInstance()
        }else
            TabBFragment.newInstance()
    }

    override fun getCount(): Int = 2
}