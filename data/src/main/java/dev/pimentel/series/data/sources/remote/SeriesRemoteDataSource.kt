package dev.pimentel.series.data.sources.remote

import dev.pimentel.series.data.body.SeriesResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesRemoteDataSource {

    @GET("/search/shows?q=:query")
    suspend fun getSeries(@Query("q") query: String?): List<SeriesResponseBody>
}
