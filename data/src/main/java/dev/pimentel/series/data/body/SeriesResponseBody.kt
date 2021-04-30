package dev.pimentel.series.data.body

import com.squareup.moshi.Json

data class SeriesResponseBody(
    @Json(name = "score") val score: Double,
    @Json(name = "show") val show: ShowResponseBody
) {

    data class ShowResponseBody(
        @Json(name = "id") val id: Int,
        @Json(name = "name") val name: String
    )
}
