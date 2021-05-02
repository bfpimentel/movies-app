package dev.pimentel.shows.domain.model

interface ShowsPageModel {
    val shows: List<ShowModel>
    val nextPage: Int
}
