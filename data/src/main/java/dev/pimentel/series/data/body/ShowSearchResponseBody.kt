package dev.pimentel.series.data.body

import com.squareup.moshi.Json

data class ShowSearchResponseBody(
    @Json(name = "show") val info: ShowResponseBody
)
