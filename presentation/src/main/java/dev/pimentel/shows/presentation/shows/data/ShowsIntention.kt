package dev.pimentel.shows.presentation.shows.data

sealed class ShowsIntention {
    object GetMoreShows : ShowsIntention()
    data class SearchShows(val query: String) : ShowsIntention()
    data class FavoriteOrRemoveShow(val showId: Int) : ShowsIntention()
    data class NavigateToInformation(val showId: Int) : ShowsIntention()
}
