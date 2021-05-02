package dev.pimentel.series.data.body

import com.squareup.moshi.Json

data class ShowSearchResponseBody(
    @Json(name = "score") val score: Double,
    @Json(name = "show") val info: ShowResponseBody
)
