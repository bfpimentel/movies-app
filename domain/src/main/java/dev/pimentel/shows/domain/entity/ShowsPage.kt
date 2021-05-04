package dev.pimentel.shows.domain.entity

data class ShowsPage(
    val shows: List<Show>,
    val nextPage: Int
)
