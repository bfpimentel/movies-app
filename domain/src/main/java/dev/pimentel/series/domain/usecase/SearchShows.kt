package dev.pimentel.series.domain.usecase

import dev.pimentel.series.domain.repository.ShowsRepository

class SearchShows(
    private val showsRepository: ShowsRepository
) : SuspendedUseCase<SearchShows.Params, Unit> {

    override suspend fun invoke(params: Params) = showsRepository.searchShows(query = params.query)

    data class Params(val query: String)
}
