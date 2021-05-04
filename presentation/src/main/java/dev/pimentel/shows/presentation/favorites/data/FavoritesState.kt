package dev.pimentel.shows.presentation.favorites.data

import dev.pimentel.shows.shared.mvi.Event
import dev.pimentel.shows.shared.shows.ShowViewData

data class FavoritesState(
    val showsEvent: Event<List<ShowViewData>>? = null
)
