package dev.pimentel.series.data.repository

import dev.pimentel.series.data.model.SeriesModelImpl
import dev.pimentel.series.data.sources.remote.SeriesRemoteDataSource
import dev.pimentel.series.domain.model.SeriesModel
import dev.pimentel.series.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.*

class SeriesRepositoryImpl(
    private val seriesRemoteDataSource: SeriesRemoteDataSource
) : SeriesRepository {

    private val searchPublisher = MutableStateFlow<String?>(null)

    override fun getSeries(): Flow<List<SeriesModel>> =
        searchPublisher
            .debounce(1000L)
            .mapLatest { query -> seriesRemoteDataSource.getSeries(query) }
            .distinctUntilChanged()
            .map { list ->
                list.map { seriesResponse ->
                    SeriesModelImpl(
                        id = seriesResponse.show.id,
                        name = seriesResponse.show.name
                    )
                }
            }

    override suspend fun searchSeries(query: String?) {
        searchPublisher.emit(query)
    }
}
