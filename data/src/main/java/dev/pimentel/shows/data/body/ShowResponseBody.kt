package dev.pimentel.shows.data.body

import com.squareup.moshi.Json

data class ShowResponseBody(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "status") val status: String,
    @Json(name = "summary") val summary: String,
    @Json(name = "rating") val rating: RatingResponseBody,
    @Json(name = "image") val image: ImageResponseBody?,
    @Json(name = "premiered") val premieredDate: String?,
    @Json(name = "schedule") val schedule: ScheduleResponseBody? = null,
    @Json(name = "_embedded") val embedded: EmbeddedResponseBody? = null
) {

    data class RatingResponseBody(
        @Json(name = "average") val average: Float?
    )

    data class ScheduleResponseBody(
        @Json(name = "time") val time: String,
        @Json(name = "days") val days: List<String>,
    )

    data class EmbeddedResponseBody(
        @Json(name = "episodes") val episodes: List<EpisodeResponseBody>
    ) {

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
    }

    data class ImageResponseBody(
        @Json(name = "original") val originalUrl: String
    )
}
