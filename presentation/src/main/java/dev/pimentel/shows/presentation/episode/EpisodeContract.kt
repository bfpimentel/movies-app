package dev.pimentel.shows.presentation.episode

import dev.pimentel.shows.presentation.episode.data.EpisodeIntention
import dev.pimentel.shows.presentation.episode.data.EpisodeState
import dev.pimentel.shows.shared.mvi.StateViewModel

interface EpisodeContract {

    interface ViewModel : StateViewModel<EpisodeState, EpisodeIntention>
}
