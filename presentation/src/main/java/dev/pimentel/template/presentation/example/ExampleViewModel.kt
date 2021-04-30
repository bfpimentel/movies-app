package dev.pimentel.template.presentation.example

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pimentel.template.di.NavigatorRouterQualifier
import dev.pimentel.template.domain.usecase.GetExample
import dev.pimentel.template.domain.usecase.NoParams
import dev.pimentel.template.presentation.example.data.ExampleIntention
import dev.pimentel.template.presentation.example.data.ExampleState
import dev.pimentel.template.shared.dispatchers.DispatchersProvider
import dev.pimentel.template.shared.mvi.StateViewModelImpl
import dev.pimentel.template.shared.navigator.NavigatorRouter
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
