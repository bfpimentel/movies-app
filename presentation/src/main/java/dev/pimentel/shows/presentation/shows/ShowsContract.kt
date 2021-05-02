package dev.pimentel.shows.presentation.shows

import dev.pimentel.shows.presentation.shows.data.ShowsIntention
import dev.pimentel.shows.presentation.shows.data.ShowsState
import dev.pimentel.shows.shared.mvi.StateViewModel

interface ShowsContract {

    interface ViewModel : StateViewModel<ShowsState, ShowsIntention>

    interface ItemListener {

    }
}
