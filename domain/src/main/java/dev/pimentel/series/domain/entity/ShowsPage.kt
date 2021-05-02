package dev.pimentel.series.domain.entity

data class ShowsPage(
    val shows: List<Show>,
    val nextPage: Int
)
