package dev.pimentel.shows.data.sources.remote

import dev.pimentel.shows.data.body.EpisodeResponseBody
import dev.pimentel.shows.data.body.ShowResponseBody
import dev.pimentel.shows.data.body.ShowSearchResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowsRemoteDataSource {

    @GET("/search/shows?q=:query")
    suspend fun getShows(@Query("q") query: String?): List<ShowSearchResponseBody>

    @GET("/shows")
    suspend fun getShows(@Query("page") page: Int): List<ShowResponseBody>

    @GET("/shows/{showId}?embed=episodes")
    suspend fun getShowInformation(@Path("showId") showId: Int): ShowResponseBody

    @GET("/shows/{showId}/episodebynumber")
    suspend fun getEpisodeInformation(
        @Path("showId") showId: Int,
        @Query("season") seasonNumber: Int,
        @Query("number") episodeNumber: Int
    ): EpisodeResponseBody
}
