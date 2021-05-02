package dev.pimentel.series.domain.model

interface ShowsPageModel {
    val shows: List<ShowModel>
    val nextPage: Int
}
