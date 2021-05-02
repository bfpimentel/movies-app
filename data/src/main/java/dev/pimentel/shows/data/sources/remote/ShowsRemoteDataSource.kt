package dev.pimentel.shows.data.sources.remote

import dev.pimentel.shows.data.body.ShowResponseBody
import dev.pimentel.shows.data.body.ShowSearchResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowsRemoteDataSource {

    @GET("/search/shows?q=:query")
    suspend fun getShows(@Query("q") query: String?): List<ShowSearchResponseBody>

    @GET("/shows")
    suspend fun getShows(@Query("page") page: Int): List<ShowResponseBody>
}
