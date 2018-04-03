package lee.jaebeom.rainist_android.main.tab_b

import io.reactivex.Observable
import lee.jaebeom.rainist_android.data.Repository
import lee.jaebeom.rainist_android.data.User
import lee.jaebeom.rainist_android.main.MainContract

/**
 * Created by leejaebeom on 2018. 4. 2..
 */

@Deprecated("Pagination 미사용으로 사용하지 않음.")
class InsideRecyclerPresenter(private val insideView: MainContract.InsideRecyclerView, private val viewHolderView: MainContract.ViewHoloerView) : MainContract.TabBInsideRecyclerPresenter{
    override fun initTotal(starGazer: User) {
        starGazer.apply {
//            starredTotalPage = starred.size.div(20)
            if (starred.size.rem(20) != 0){
//                starredTotalPage++
            }
        }

    }

    override fun initCurrent(starGazer: User) {
//        starGazer.starredCurrentPage = 1
    }
    override fun getStarredData(starGazer: User, starred: ArrayList<Repository>) {
        starGazer.run {
            Observable.fromIterable(this.starred)
//                    .skip(20 * this.starredCurrentPage.toLong() - 1)
//                    .take(20 * this.starredCurrentPage.toLong())
                    .subscribe {
                        starred.add(it)
                    }
            notifyListDataChange()
//            this.starredCurrentPage += 1
            viewHolderView.hideProgressBar()
//            viewHolderView.isLoading()
        }
    }


    override fun notifyListDataChange() {
        insideView.notifyListDatachange()
    }


}