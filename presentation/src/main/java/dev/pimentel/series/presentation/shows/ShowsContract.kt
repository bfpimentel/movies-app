package dev.pimentel.series.presentation.shows

import dev.pimentel.series.presentation.shows.data.ShowsIntention
import dev.pimentel.series.presentation.shows.data.ShowsState
import dev.pimentel.series.shared.mvi.StateViewModel

interface ShowsContract {

    interface ViewModel : StateViewModel<ShowsState, ShowsIntention>

    interface ItemListener {

    }
}
