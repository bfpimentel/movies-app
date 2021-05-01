package dev.pimentel.series.presentation.shows.data

sealed class ShowsIntention {
    object GetShows : ShowsIntention()
    data class SearchShows(val query: String) : ShowsIntention()
}
