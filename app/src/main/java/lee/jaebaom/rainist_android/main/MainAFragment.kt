package lee.jaebaom.rainist_android.main

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class MainAFragment : Fragment() {
    companion object {
        fun newInstance(): MainAFragment{
            val args = Bundle()
            var fragment = MainAFragment()
            fragment.arguments = args
            return fragment
        }
    }
}