package dev.pimentel.series.presentation.shows.data

import dev.pimentel.series.shared.mvi.Event

data class ShowsState(
    val showsEvent: Event<List<ShowViewData>>? = null
)
