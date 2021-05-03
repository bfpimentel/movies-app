package dev.pimentel.shows.presentation.details

import dev.pimentel.shows.presentation.details.data.DetailsIntention
import dev.pimentel.shows.presentation.details.data.DetailsState
import dev.pimentel.shows.shared.mvi.StateViewModel

interface DetailsContract {

    interface ViewModel : StateViewModel<DetailsState, DetailsIntention>
}
