package under.the.bridge.data.model

data class TrackArtist(
        val message: Message
) {
    data class Message(
            val body: Body,
            val header: Header
    ) {
        data class Body(
                val track_list: List<Track>
        ) {
            data class Track(
                    val track: Track? = null
            ) {
                data class Track(
                        val track_id: Int = 0,
                        val artist_name: String = "",
                        val track_name: String = "",
                        val artist_id: Int = 0,
                        val album_id: Int = 0,
                        val album_name: String = "",
                        val commontrack_id: Int = 0,
                        val explicit: Int = 0,
                        val has_lyrics: Int = 0,
                        val has_richsync: Int = 0,
                        val has_subtitles: Int = 0,
                        val instrumental: Int = 0,
                        val num_favourite: Int = 0,
                        val primary_genres: PrimaryGenres? = null,
                        val restricted: Int = 0,
                        val track_edit_url: String = "",
                        val track_name_translation_list: List<Any> = emptyList(),
                        val track_rating: Int = 0,
                        val track_share_url: String = "",
                        val updated_time: String = ""
                ) {
                    data class PrimaryGenres(
                            val music_genre_list: List<Any>
                    )
                }
            }
        }

        data class Header(
                val available: Int,
                val execute_time: Double,
                val status_code: Int
        )
    }

    companion object {
        fun new(id: Int, artist: String, title: String) = Message.Body.Track(Message.Body.Track.Track(
                id, artist, title
        )
        )
    }
}