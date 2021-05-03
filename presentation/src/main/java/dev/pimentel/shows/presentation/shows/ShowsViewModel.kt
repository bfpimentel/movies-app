package dev.pimentel.shows.presentation.shows

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pimentel.shows.domain.usecase.*
import dev.pimentel.shows.presentation.shows.data.ShowViewData
import dev.pimentel.shows.presentation.shows.data.ShowsIntention
import dev.pimentel.shows.presentation.shows.data.ShowsState
import dev.pimentel.shows.shared.dispatchers.DispatchersProvider
import dev.pimentel.shows.shared.mvi.StateViewModelImpl
import dev.pimentel.shows.shared.mvi.toEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val getShows: GetShows,
    private val getMoreShows: GetMoreShows,
    private val favoriteOrRemoveShow: FavoriteOrRemoveShow,
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
            is ShowsIntention.FavoriteShow -> favoriteOrRemoveShow(FavoriteOrRemoveShow.Params(intention.showId))
        }
    }

    private suspend fun getShows() {
        try {
            getShows(NoParams).collect { showsPage ->
                val showsViewData = showsPage.shows.map { show ->
                    ShowViewData(
                        id = show.id,
                        imageUrl = show.imageUrl,
                        name = show.name,
                        premieredDate = show.premieredDate,
                        status = show.status,
                        rating = (show.rating ?: DEFAULT_RATING) / FIVE_STAR_RATING_DIVIDER,
                        isFavorite = show.isFavorite
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
        const val DEFAULT_RATING = 0F
        const val FIVE_STAR_RATING_DIVIDER = 2
    }
}
