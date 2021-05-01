package dev.pimentel.series.domain.usecase

import dev.pimentel.series.domain.entity.Series
import dev.pimentel.series.domain.repository.ShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSeries(
    private val showsRepository: ShowsRepository
) : UseCase<NoParams, Flow<List<Series>>> {

    override fun invoke(params: NoParams): Flow<List<Series>> =
        showsRepository.getSeries().map { list ->
            list.map { seriesModel -> Series(id = seriesModel.id, name = seriesModel.name) }
        }
}
