package dev.pimentel.shows.presentation.information

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pimentel.shows.domain.entity.ShowInformation
import dev.pimentel.shows.domain.usecase.FavoriteOrRemoveShow
import dev.pimentel.shows.domain.usecase.GetShowInformation
import dev.pimentel.shows.domain.usecase.NoParams
import dev.pimentel.shows.domain.usecase.SearchShowInformation
import dev.pimentel.shows.presentation.information.data.InformationIntention
import dev.pimentel.shows.presentation.information.data.InformationState
import dev.pimentel.shows.presentation.information.data.InformationViewData
import dev.pimentel.shows.shared.dispatchers.DispatchersProvider
import dev.pimentel.shows.shared.mvi.StateViewModelImpl
import dev.pimentel.shows.shared.mvi.toEvent
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
            is InformationIntention.SearchShowInformation -> searchInformation(intention.showId)
            is InformationIntention.FavoriteOrRemoveShow -> favoriteOrRemoveShow()
        }
    }

    private suspend fun getShowInformation() {
        try {
            getShowInformation(NoParams).collect { showInformation ->
                // TODO: Need to abstract mapping to separate class
                val viewData = InformationViewData(
                    name = showInformation.name,
                    summary = showInformation.summary,
                    status = showInformation.status,
                    premieredDate = showInformation.premieredDate ?: "Unknown",
                    rating = (showInformation.rating ?: 0F) / 2,
                    imageUrl = showInformation.imageUrl,
                    isFavorite = showInformation.isFavorite,
                    schedule = showInformation.schedule?.days?.joinToString { day ->
                        "$day at ${showInformation.schedule?.time}"
                    },
                    seasons = showInformation.episodes.groupBy(ShowInformation.Episode::season)
                        .map { (season, episodes) ->
                            InformationViewData.SeasonViewData(
                                number = season,
                                episodes = episodes.map { episode ->
                                    InformationViewData.SeasonViewData.EpisodeViewData(
                                        id = episode.id,
                                        number = episode.number,
                                        name = episode.name
                                    )
                                }
                            )
                        }
                )

                updateState { copy(informationEvent = viewData.toEvent()) }
            }
        } catch (error: Exception) {
            Log.d("GET_SHOW_INFORMATION", "ERROR", error)
        }
    }

    private suspend fun searchInformation(showId: Int) {
        this.showId = showId
        searchShowInformation(SearchShowInformation.Params(showId))
    }

    private suspend fun favoriteOrRemoveShow() {
        favoriteOrRemoveShow(FavoriteOrRemoveShow.Params(this.showId!!))
    }
}
