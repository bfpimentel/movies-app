package dev.pimentel.shows.data.body

import com.squareup.moshi.Json

data class EpisodeResponseBody(
    @Json(name = "id") val id: Int,
    @Json(name = "number") val number: Int,
    @Json(name = "season") val season: Int,
    @Json(name = "name") val name: String,
    @Json(name = "summary") val summary: String,
    @Json(name = "image") val image: ImageResponseBody?,
    @Json(name = "airdate") val airDate: String,
    @Json(name = "airtime") val airTime: String
)
