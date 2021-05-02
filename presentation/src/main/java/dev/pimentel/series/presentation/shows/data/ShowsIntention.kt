package dev.pimentel.series.presentation.shows.data

sealed class ShowsIntention {
    object GetMoreShows : ShowsIntention()
    data class SearchShows(val query: String) : ShowsIntention()
}
