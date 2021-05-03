package dev.pimentel.shows.domain.entity

data class ShowInformation(
    val id: Int,
    val name: String,
    val summary: String,
    val status: String,
    val premieredDate: String?,
    val rating: Float?,
    val imageUrl: String?,
    val isFavorite: Boolean,
    val schedule: Schedule?,
    val episodes: List<Episode>
) {

    data class Schedule(
        val time: String,
        val days: List<String>
    )

    data class Episode(
        val id: Int,
        val number: Int,
        val season: Int,
        val name: String,
        val summary: String,
        val imageUrl: String?,
        val airDate: String,
        val airTime: String
    )
}
