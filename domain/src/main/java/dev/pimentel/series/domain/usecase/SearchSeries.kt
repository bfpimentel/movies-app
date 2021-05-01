package dev.pimentel.series.domain.usecase

import dev.pimentel.series.domain.entity.Show
import dev.pimentel.series.domain.repository.ShowsRepository

class SearchSeries(
    private val showsRepository: ShowsRepository
) : SuspendedUseCase<SearchSeries.Params, List<Show>> {

    override suspend fun invoke(params: Params): List<Show> =
        showsRepository.searchShows(params.query).map { showModel ->
            Show(id = showModel.id, name = showModel.name)
        }

    data class Params(val query: String)
}
