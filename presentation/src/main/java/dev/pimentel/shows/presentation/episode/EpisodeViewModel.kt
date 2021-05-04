package dev.pimentel.shows.presentation.episode

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pimentel.shows.domain.usecase.GetEpisodeInformation
import dev.pimentel.shows.presentation.episode.data.EpisodeIntention
import dev.pimentel.shows.presentation.episode.data.EpisodeState
import dev.pimentel.shows.shared.dispatchers.DispatchersProvider
import dev.pimentel.shows.shared.mvi.StateViewModelImpl
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val getEpisodeInformation: GetEpisodeInformation,
    dispatchersProvider: DispatchersProvider,
    @EpisodeStateQualifier initialState: EpisodeState
) : StateViewModelImpl<EpisodeState, EpisodeIntention>(
    dispatchersProvider = dispatchersProvider,
    initialState = initialState
), EpisodeContract.ViewModel {

    override suspend fun handleIntentions(intention: EpisodeIntention) {
        when (intention) {
            is EpisodeIntention.GetEpisode -> getEpisode(
                showId = intention.showId,
                seasonNumber = intention.seasonNumber,
                episodeNumber = intention.episodeNumber
            )
        }
    }

    private suspend fun getEpisode(showId: Int, seasonNumber: Int, episodeNumber: Int) {
        try {
            val episode = getEpisodeInformation(
                GetEpisodeInformation.Params(
                    showId = showId,
                    seasonNumber = seasonNumber,
                    episodeNumber = episodeNumber
                )
            )

            updateState {
                copy(
                    number = episode.number,
                    season = episode.season,
                    name = episode.name,
                    summary = episode.summary,
                    imageUrl = episode.imageUrl,
                    airDate = episode.airDate,
                    airTime = episode.airTime
                )
            }
        } catch (error: Exception) {
            Log.d("GET_EPISODE", "ERROR", error)
        }
    }
}
