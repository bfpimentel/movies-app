package dev.pimentel.series.presentation.series

import dev.pimentel.series.presentation.series.data.SeriesIntention
import dev.pimentel.series.presentation.series.data.SeriesState
import dev.pimentel.series.shared.mvi.StateViewModel

interface SeriesContract {

    interface ViewModel : StateViewModel<SeriesState, SeriesIntention>
}
