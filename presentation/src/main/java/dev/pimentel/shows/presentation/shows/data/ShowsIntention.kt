package dev.pimentel.shows.presentation.shows.data

sealed class ShowsIntention {
    object GetMoreShows : ShowsIntention()
    data class SearchShows(val query: String) : ShowsIntention()
    data class FavoriteShow(val showId: Int) : ShowsIntention()
}
