package dev.pimentel.shows.presentation.information.data

import dev.pimentel.shows.shared.mvi.Event

data class InformationState(
    val informationEvent: Event<InformationViewData>? = null
)
