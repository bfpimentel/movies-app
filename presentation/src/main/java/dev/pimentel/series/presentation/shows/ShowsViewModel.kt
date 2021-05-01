package dev.pimentel.series.presentation.shows

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pimentel.series.domain.usecase.GetShows
import dev.pimentel.series.domain.usecase.SearchShows
import dev.pimentel.series.presentation.shows.data.ShowViewData
import dev.pimentel.series.presentation.shows.data.ShowsIntention
import dev.pimentel.series.presentation.shows.data.ShowsState
import dev.pimentel.series.shared.dispatchers.DispatchersProvider
import dev.pimentel.series.shared.mvi.StateViewModelImpl
import dev.pimentel.series.shared.mvi.toEvent
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val getShows: GetShows,
    private val searchShows: SearchShows,
    dispatchersProvider: DispatchersProvider,
    @WelcomeStateQualifier initialState: ShowsState
) : StateViewModelImpl<ShowsState, ShowsIntention>(
    dispatchersProvider = dispatchersProvider,
    initialState = initialState
), ShowsContract.ViewModel {

    private var currentPage = 0

    override suspend fun handleIntentions(intention: ShowsIntention) {
        when (intention) {
            is ShowsIntention.GetShows -> getShows()
            is ShowsIntention.SearchShows -> searchShows(intention.query)
        }
    }

    private suspend fun getShows() {
        try {
            val shows = getShows(GetShows.Params(page = this.currentPage))

            this.currentPage++

            val showsViewData = shows.map {
                ShowViewData(
                    id = it.id,
                    imageUrl = "https://static.tvmaze.com/uploads/images/medium_portrait/0/2400.jpg",
                    name = it.name,
                    premieredDate = "2021-04-22",
                    status = "Ended",
                    rating = Random(10).nextDouble()
                )
            }

            updateState { copy(showsEvent = showsViewData.toEvent()) }
        } catch (error: Exception) {
            Log.d("GET_SHOWS", "ERROR", error)
            // TODO: Show error
        }
    }

    private suspend fun searchShows(query: String) {
        try {
            searchShows(SearchShows.Params(query))

            this.currentPage = INITIAL_PAGE
        } catch (error: Exception) {

        }
    }

    private companion object {
        const val INITIAL_PAGE = 0
    }
}
