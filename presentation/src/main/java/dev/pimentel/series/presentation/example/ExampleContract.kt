package dev.pimentel.series.presentation.example

import dev.pimentel.series.presentation.example.data.ExampleIntention
import dev.pimentel.series.presentation.example.data.ExampleState
import dev.pimentel.series.shared.mvi.StateViewModel

interface ExampleContract {

    interface ViewModel : StateViewModel<ExampleState, ExampleIntention>
}
