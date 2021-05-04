package dev.pimentel.shows.presentation.favorites

import dev.pimentel.shows.presentation.favorites.data.FavoritesIntention
import dev.pimentel.shows.presentation.favorites.data.FavoritesState
import dev.pimentel.shows.shared.mvi.StateViewModel

class FavoritesContract {

    interface ViewModel : StateViewModel<FavoritesState, FavoritesIntention>
}
