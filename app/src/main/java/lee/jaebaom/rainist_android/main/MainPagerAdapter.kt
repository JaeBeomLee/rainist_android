package lee.jaebaom.rainist_android.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class MainPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {

    // TODO 이름 변경할것
    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }

    override fun getItem(position: Int): Fragment {
        if (position == 0){
            return MainTabFragment.newInstance()
        }else
            return MainTabFragmentTemp.newInstance()
    }

    override fun getCount(): Int = 2
}