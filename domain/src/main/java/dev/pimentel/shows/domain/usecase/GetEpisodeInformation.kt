package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.entity.Episode
import dev.pimentel.shows.domain.repository.ShowsRepository

class GetEpisodeInformation(
    private val showsRepository: ShowsRepository
) : SuspendedUseCase<GetEpisodeInformation.Params, Episode> {

    override suspend fun invoke(params: Params): Episode = showsRepository.getEpisodeInformation(
        showId = params.showId,
        seasonNumber = params.seasonNumber,
        episodeNumber = params.episodeNumber,
    ).let { episodeModel ->
        Episode(
            id = episodeModel.id,
            number = episodeModel.number,
            season = episodeModel.season,
            name = episodeModel.name,
            summary = episodeModel.summary,
            imageUrl = episodeModel.imageUrl,
            airDate = episodeModel.airDate,
            airTime = episodeModel.airTime
        )
    }

    data class Params(
        val showId: Int,
        val seasonNumber: Int,
        val episodeNumber: Int
    )
}
