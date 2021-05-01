package dev.pimentel.series.domain.usecase

import dev.pimentel.series.domain.entity.Show
import dev.pimentel.series.domain.repository.ShowsRepository

class GetShows(private val showsRepository: ShowsRepository) : SuspendedUseCase<GetShows.Params, List<Show>> {

    override suspend fun invoke(params: Params): List<Show> =
        showsRepository.getShows(page = params.page).map { seriesModel ->
            Show(id = seriesModel.id, name = seriesModel.name)
        }

    data class Params(val page: Int)
}
