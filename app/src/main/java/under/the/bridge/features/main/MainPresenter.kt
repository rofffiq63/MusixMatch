package under.the.bridge.features.main

import under.the.bridge.data.DataManager
import under.the.bridge.data.model.TrackArtist
import under.the.bridge.injection.ConfigPersistent
import under.the.bridge.features.base.BasePresenter
import under.the.bridge.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<MainMvpView>() {

    override fun attachView(mvpView: MainMvpView) {
        super.attachView(mvpView)
    }

    fun getData(search: String) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getTrackArtist(search)
                .compose(SchedulerUtils.ioToMain<TrackArtist>())
                .subscribe({ trackArtist ->
                    mvpView?.showProgress(false)
                    mvpView?.getDataSuccess(trackArtist.message.body.track_list)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

}