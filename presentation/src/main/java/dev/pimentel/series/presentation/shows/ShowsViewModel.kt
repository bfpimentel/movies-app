package dev.pimentel.series.presentation.shows

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pimentel.series.domain.usecase.GetMoreShows
import dev.pimentel.series.domain.usecase.GetShows
import dev.pimentel.series.domain.usecase.NoParams
import dev.pimentel.series.domain.usecase.SearchShows
import dev.pimentel.series.presentation.shows.data.ShowViewData
import dev.pimentel.series.presentation.shows.data.ShowsIntention
import dev.pimentel.series.presentation.shows.data.ShowsState
import dev.pimentel.series.shared.dispatchers.DispatchersProvider
import dev.pimentel.series.shared.mvi.StateViewModelImpl
import dev.pimentel.series.shared.mvi.toEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val getShows: GetShows,
    private val getMoreShows: GetMoreShows,
    private val searchShows: SearchShows,
    dispatchersProvider: DispatchersProvider,
    @WelcomeStateQualifier initialState: ShowsState
) : StateViewModelImpl<ShowsState, ShowsIntention>(
    dispatchersProvider = dispatchersProvider,
    initialState = initialState
), ShowsContract.ViewModel {

    private var nextPage = INITIAL_PAGE

    init {
        viewModelScope.launch(dispatchersProvider.io) { getShows() }
    }

    override suspend fun handleIntentions(intention: ShowsIntention) {
        when (intention) {
            is ShowsIntention.GetMoreShows -> getMoreShows()
            is ShowsIntention.SearchShows -> searchShows(intention.query)
        }
    }

    private suspend fun getShows() {
        try {
            getShows(NoParams).collect { showsPage ->
                val showsViewData = showsPage.shows.map {
                    ShowViewData(
                        id = it.id,
                        imageUrl = it.imageUrl,
                        name = it.name,
                        premieredDate = it.premieredDate,
                        status = it.status,
                        rating = (it.rating ?: 0F) / 2
                    )
                }

                this.nextPage = showsPage.nextPage

                updateState { copy(showsEvent = showsViewData.toEvent()) }
            }
        } catch (error: Exception) {
            Log.d("GET_SHOWS", "ERROR", error)
            // TODO: Map and show errors
        }
    }

    private suspend fun getMoreShows() {
        if (this.nextPage == GetShows.NO_MORE_PAGES) return
        getMoreShows(GetMoreShows.Params(nextPage = this.nextPage))
    }

    private suspend fun searchShows(query: String) {
        searchShows(SearchShows.Params(query = query))
    }

    private companion object {
        const val INITIAL_PAGE = 0
    }
}
