package dev.pimentel.series.domain.usecase

import dev.pimentel.series.domain.entity.Series
import dev.pimentel.series.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSeries(
    private val seriesRepository: SeriesRepository
) : UseCase<NoParams, Flow<List<Series>>> {

    override fun invoke(params: NoParams): Flow<List<Series>> =
        seriesRepository.getSeries().map { list ->
            list.map { seriesModel -> Series(id = seriesModel.id, name = seriesModel.name) }
        }
}
