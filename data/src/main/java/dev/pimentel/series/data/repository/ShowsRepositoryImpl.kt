package dev.pimentel.series.data.repository

import dev.pimentel.series.data.dto.ShowDTO
import dev.pimentel.series.data.model.ShowsModelImpl
import dev.pimentel.series.data.sources.local.ShowsLocalDataSource
import dev.pimentel.series.data.sources.remote.ShowsRemoteDataSource
import dev.pimentel.series.domain.model.ShowsModel
import dev.pimentel.series.domain.repository.ShowsRepository
import kotlinx.coroutines.flow.*

class ShowsRepositoryImpl(
    private val showsRemoteDataSource: ShowsRemoteDataSource,
    private val showsLocalDataSource: ShowsLocalDataSource
) : ShowsRepository {

    private val getMoreSeriesPublisher = MutableStateFlow<Int?>(null)

    override fun getSeries(): Flow<List<ShowsModel>> = flow {
        val result = mutableListOf<ShowsModel>()

        getMoreSeriesPublisher.map { page ->
            val currentPage = page ?: 0
            if (currentPage == 0) result.clear()
            return@map currentPage
        }.mapLatest { page ->
            val lastSavedPage = showsLocalDataSource.getLastShowId() / 250

            if (lastSavedPage >= page) {
                showsLocalDataSource.getShows(page).map { seriesResponse ->
                    ShowsModelImpl(
                        id = seriesResponse.id,
                        name = seriesResponse.name
                    )
                }
            } else {
                val shows = showsRemoteDataSource.getShows(page = page)
                val showsToBeInserted = shows.map { show ->
                    ShowDTO(id = show.id, name = show.name)
                }

                showsLocalDataSource.insertShows(showsToBeInserted)

                shows.map { seriesResponse ->
                    ShowsModelImpl(
                        id = seriesResponse.id,
                        name = seriesResponse.name
                    )
                }
            }
        }.distinctUntilChanged().collect { seriesModels ->
            result.addAll(seriesModels)
            emit(result)
        }
    }

    override suspend fun getMoreSeries(nextPage: Int) = getMoreSeriesPublisher.emit(nextPage)

    override suspend fun searchSeries(query: String?): List<ShowsModel> =
        showsRemoteDataSource.getShows(query = query).map { seriesResponse ->
            ShowsModelImpl(
                id = seriesResponse.show.id,
                name = seriesResponse.show.name
            )
        }
}
