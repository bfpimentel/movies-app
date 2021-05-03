package dev.pimentel.shows.presentation.details

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pimentel.shows.presentation.details.data.DetailsIntention
import dev.pimentel.shows.presentation.details.data.DetailsState
import dev.pimentel.shows.shared.dispatchers.DispatchersProvider
import dev.pimentel.shows.shared.mvi.StateViewModelImpl
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    dispatchersProvider: DispatchersProvider,
    @DetailsStateQualifier initialState: DetailsState
) : StateViewModelImpl<DetailsState, DetailsIntention>(
    dispatchersProvider = dispatchersProvider,
    initialState = initialState
), DetailsContract.ViewModel {

    override suspend fun handleIntentions(intention: DetailsIntention) {
    }
}
