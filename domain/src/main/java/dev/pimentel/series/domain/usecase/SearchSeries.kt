package dev.pimentel.series.domain.usecase

import dev.pimentel.series.domain.repository.ShowsRepository

class SearchSeries(
    private val showsRepository: ShowsRepository
) : SuspendedUseCase<SearchSeries.Params, Unit> {

    override suspend fun invoke(params: Params) = showsRepository.searchSeries(params.query)

    data class Params(val query: String?)
}
