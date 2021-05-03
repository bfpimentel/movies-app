package dev.pimentel.shows.presentation.favorites.data

sealed class FavoritesIntention {

    data class SearchFavorites(val query: String?) : FavoritesIntention()

    data class FavoriteOrRemoveShow(val showId: Int) : FavoritesIntention()
}
