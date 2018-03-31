package lee.jaebaom.rainist_android.main

import android.content.Context

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
interface MainContract {
    interface View {
        fun initPresenter(presenter: Presenter)
    }

    interface Presenter {

    }

    interface TabView {
        fun isLoading()
        fun initTotal(data: String)
    }

    interface TabPresenter {

    }

    interface RecyclerView{
        fun initPresenter(presenter: RecyclerPresenter)
        fun notifyListDatachange()
    }

    interface RecyclerPresenter{
        fun getInitData(context: Context?, page: Int)
        fun getData(context: Context?, page: Int)
    }
}