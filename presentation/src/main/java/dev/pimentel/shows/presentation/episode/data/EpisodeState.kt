package dev.pimentel.shows.presentation.episode.data

data class EpisodeState(
    val number: Int = 0,
    val season: Int = 0,
    val name: String = "",
    val summary: String = "",
    val imageUrl: String? = null,
    val airDate: String = "",
    val airTime: String = ""
)
