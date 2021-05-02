package dev.pimentel.shows.data.body

import com.squareup.moshi.Json

data class ShowResponseBody(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "status") val status: String,
    @Json(name = "premiered") val premieredDate: String,
    @Json(name = "rating") val rating: RatingResponseBody,
    @Json(name = "image") val image: ImageResponseBody,
) {

    data class RatingResponseBody(
        @Json(name = "average") val average: Float?
    )

    data class ImageResponseBody(
        @Json(name = "original") val originalUrl: String
    )
}
