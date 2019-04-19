package under.the.bridge.data

import under.the.bridge.data.remote.ApiHandler
import io.reactivex.Single
import under.the.bridge.data.model.TrackArtist
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject
constructor(private val mApiHandler: ApiHandler) {

    fun getApiKey() = "c19250168f84de8098f8224919ef45ee"

    fun getTrackArtist(search: String): Single<TrackArtist> {
        return mApiHandler.getTrackArtist(search, getApiKey())
    }

}