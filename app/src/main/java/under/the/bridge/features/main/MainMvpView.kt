package under.the.bridge.features.main

import under.the.bridge.data.model.TrackArtist
import under.the.bridge.features.base.MvpView

interface MainMvpView : MvpView {

    fun getDataSuccess(pokemon: List<TrackArtist.Message.Body.Track>)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}