package lee.jaebeom.rainist_android.main.tab_a

import io.reactivex.Observable
import lee.jaebeom.rainist_android.data.Repository
import lee.jaebeom.rainist_android.data.User
import lee.jaebeom.rainist_android.main.MainContract

/**
 * Created by leejaebeom on 2018. 4. 2..
 */
@Deprecated("Pagination 미사용으로 사용하지 않음.")
class InsideRecyclerPresenter(private val insideView: MainContract.InsideRecyclerView, private val viewHolderView: MainContract.ViewHoloerView) : MainContract.TabAInsideRecyclerPresenter{
    override fun initTotal(repository: Repository?) {
        repository?.apply {
//            starGazersTotalPage = starGazers?.size?.div(20)
            if (starGazers?.size?.rem(20) != 0){
//                starGazersTotalPage?.plus(1)
            }
        }

    }

    override fun initCurrent(repository: Repository?) {
//        repository?.starGazersCurrentPage = 1
    }

    override fun getStarGazersData(repository: Repository?, starGazers: ArrayList<User>) {
        repository?.run {

            Observable.fromIterable(this.starGazers)
//                    .skip(20 * this.starGazersCurrentPage.toLong() - 1)
//                    .take(20 * this.starGazersCurrentPage.toLong())
                    .subscribe {
                        starGazers.add(it)
                    }
            notifyListDataChange()
//            this.starGazersCurrentPage += 1
            viewHolderView.hideProgressBar()
            viewHolderView.isLoading()
        }
    }

    override fun notifyListDataChange() {
        insideView.notifyListDatachange()
    }


}