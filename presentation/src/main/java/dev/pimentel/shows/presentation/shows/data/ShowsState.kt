package dev.pimentel.shows.presentation.shows.data

import dev.pimentel.shows.shared.shows.ShowViewData
import dev.pimentel.shows.shared.mvi.Event

data class ShowsState(
    val showsEvent: Event<List<ShowViewData>>? = null
)
