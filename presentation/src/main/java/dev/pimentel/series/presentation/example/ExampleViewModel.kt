package dev.pimentel.series.presentation.example

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pimentel.series.di.NavigatorRouterQualifier
import dev.pimentel.series.domain.usecase.GetExample
import dev.pimentel.series.domain.usecase.NoParams
import dev.pimentel.series.presentation.example.data.ExampleIntention
import dev.pimentel.series.presentation.example.data.ExampleState
import dev.pimentel.series.shared.dispatchers.DispatchersProvider
import dev.pimentel.series.shared.mvi.StateViewModelImpl
import dev.pimentel.series.shared.navigator.NavigatorRouter
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val getExample: GetExample,
    dispatchersProvider: DispatchersProvider,
    @WelcomeStateQualifier initialState: ExampleState
) : StateViewModelImpl<ExampleState, ExampleIntention>(
    dispatchersProvider = dispatchersProvider,
    initialState = initialState
), ExampleContract.ViewModel {

    override suspend fun handleIntentions(intention: ExampleIntention) {
        when (intention) {
            ExampleIntention.GetExample -> getExample()
        }
    }

    private suspend fun getExample() {
        val example = getExample(NoParams)
        updateState { copy(example = example.value) }
    }
}
