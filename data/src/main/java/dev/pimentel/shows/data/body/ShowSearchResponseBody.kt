package dev.pimentel.shows.data.body

import com.squareup.moshi.Json

data class ShowSearchResponseBody(
    @Json(name = "show") val info: ShowResponseBody
)
