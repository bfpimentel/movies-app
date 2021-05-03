package dev.pimentel.shows.presentation.information

import dev.pimentel.shows.presentation.information.data.InformationIntention
import dev.pimentel.shows.presentation.information.data.InformationState
import dev.pimentel.shows.shared.mvi.StateViewModel

interface InformationContract {

    interface ViewModel : StateViewModel<InformationState, InformationIntention>
}
