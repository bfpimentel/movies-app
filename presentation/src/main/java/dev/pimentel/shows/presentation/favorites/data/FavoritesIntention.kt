package dev.pimentel.shows.presentation.favorites.data

sealed class FavoritesIntention {
    data class SearchFavorites(val query: String? = null) : FavoritesIntention()
    data class FavoriteOrRemoveShow(val showId: Int) : FavoritesIntention()
    data class NavigateToInformation(val showId: Int) : FavoritesIntention()
}
