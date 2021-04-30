package dev.pimentel.series.domain.usecase

import dev.pimentel.series.domain.repository.SeriesRepository

class SearchSeries(
    private val seriesRepository: SeriesRepository
) : SuspendedUseCase<SearchSeries.Params, Unit> {

    override suspend fun invoke(params: Params) = seriesRepository.searchSeries(params.query)

    data class Params(val query: String?)
}
