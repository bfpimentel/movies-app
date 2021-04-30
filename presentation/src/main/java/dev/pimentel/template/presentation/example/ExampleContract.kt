package dev.pimentel.template.presentation.example

import dev.pimentel.template.presentation.example.data.ExampleIntention
import dev.pimentel.template.presentation.example.data.ExampleState
import dev.pimentel.template.shared.mvi.StateViewModel

interface ExampleContract {

    interface ViewModel : StateViewModel<ExampleState, ExampleIntention>
}
