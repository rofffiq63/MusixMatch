package under.the.bridge.data.remote


import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import under.the.bridge.data.model.TrackArtist

interface ApiHandler {

    @GET("track.search")
    fun getTrackArtist(@Query("q_artist") search: String,
                       @Query("apikey") apikey: String): Single<TrackArtist>

}
