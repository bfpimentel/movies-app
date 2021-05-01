package dev.pimentel.series.data.body

import com.squareup.moshi.Json

data class ShowResponseBody(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)
