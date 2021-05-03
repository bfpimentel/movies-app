package dev.pimentel.shows.data.body

import com.squareup.moshi.Json

data class ImageResponseBody(
    @Json(name = "original") val originalUrl: String
)
