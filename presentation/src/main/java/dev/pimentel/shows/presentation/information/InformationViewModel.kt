package dev.pimentel.shows.presentation.information

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pimentel.shows.domain.usecase.FavoriteOrRemoveShow
import dev.pimentel.shows.domain.usecase.GetShowInformation
import dev.pimentel.shows.domain.usecase.NoParams
import dev.pimentel.shows.domain.usecase.SearchShowInformation
import dev.pimentel.shows.presentation.information.data.InformationIntention
import dev.pimentel.shows.presentation.information.data.InformationState
import dev.pimentel.shows.shared.dispatchers.DispatchersProvider
import dev.pimentel.shows.shared.mvi.StateViewModelImpl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
    private val getShowInformation: GetShowInformation,
    private val searchShowInformation: SearchShowInformation,
    private val favoriteOrRemoveShow: FavoriteOrRemoveShow,
    dispatchersProvider: DispatchersProvider,
    @InformationStateQualifier initialState: InformationState
) : StateViewModelImpl<InformationState, InformationIntention>(
    dispatchersProvider = dispatchersProvider,
    initialState = initialState
), InformationContract.ViewModel {

    private var showId: Int? = null

    init {
        viewModelScope.launch(dispatchersProvider.io) { getShowInformation() }
    }

    override suspend fun handleIntentions(intention: InformationIntention) {
        when (intention) {
            is InformationIntention.SearchShowInformation ->
                searchShowInformation(SearchShowInformation.Params(intention.showId))
            is InformationIntention.FavoriteOrRemoveShow ->
                this.showId?.let { showId -> favoriteOrRemoveShow(FavoriteOrRemoveShow.Params(showId)) }
        }
    }

    private suspend fun getShowInformation() {
        try {
            getShowInformation(NoParams).collect { showInformation ->
                showId = showInformation.id
                updateState { copy(name = showInformation.name) }
            }
        } catch (error: Exception) {
            Log.d("GET_SHOW_INFORMATION", "ERROR", error)
        }
    }
}
