package dev.pimentel.shows.presentation.episode.data

sealed class EpisodeIntention {

    data class GetEpisode(
        val showId: Int,
        val seasonNumber: Int,
        val episodeNumber: Int
    ) : EpisodeIntention()
}
