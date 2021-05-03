package dev.pimentel.shows.domain.entity

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
