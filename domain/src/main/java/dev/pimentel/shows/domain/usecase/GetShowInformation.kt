package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.entity.Episode
import dev.pimentel.shows.domain.entity.ShowInformation
import dev.pimentel.shows.domain.repository.ShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetShowInformation(private val showsRepository: ShowsRepository) : UseCase<NoParams, Flow<ShowInformation>> {

    override fun invoke(params: NoParams): Flow<ShowInformation> =
        showsRepository.getShowInformation().map { showModel ->
            ShowInformation(
                id = showModel.id,
                name = showModel.name,
                status = showModel.status,
                summary = showModel.summary,
                premieredDate = showModel.premieredDate,
                rating = showModel.rating,
                imageUrl = showModel.imageUrl,
                isFavorite = showModel.isFavorite,
                schedule = showModel.schedule?.let { schedule ->
                    ShowInformation.Schedule(
                        time = schedule.time,
                        days = schedule.days
                    )
                },
                episodes = showModel.episodes.map { episode ->
                    Episode(
                        id = episode.id,
                        number = episode.number,
                        season = episode.season,
                        name = episode.name,
                        summary = episode.summary,
                        imageUrl = episode.imageUrl,
                        airDate = episode.airDate,
                        airTime = episode.airTime
                    )
                }
            )
        }
}
