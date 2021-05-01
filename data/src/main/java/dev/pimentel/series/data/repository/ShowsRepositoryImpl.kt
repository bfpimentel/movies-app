package dev.pimentel.series.data.repository

import dev.pimentel.series.data.dto.ShowDTO
import dev.pimentel.series.data.model.ShowModelImpl
import dev.pimentel.series.data.sources.local.ShowsLocalDataSource
import dev.pimentel.series.data.sources.remote.ShowsRemoteDataSource
import dev.pimentel.series.domain.model.ShowModel
import dev.pimentel.series.domain.repository.ShowsRepository

class ShowsRepositoryImpl(
    private val showsRemoteDataSource: ShowsRemoteDataSource,
    private val showsLocalDataSource: ShowsLocalDataSource
) : ShowsRepository {

    override suspend fun getShows(page: Int): List<ShowModel> {
        if (showsLocalDataSource.getLastShowId() / SHOWS_PER_PAGE <= page) {
            val shows = showsRemoteDataSource.getShows(page = page)
            val showsToBeSaved = shows.map { show ->
                ShowDTO(id = show.id, name = show.name)
            }
            showsLocalDataSource.saveShows(showsToBeSaved)
        }

        return showsLocalDataSource.getShows(page = page)
            .map { show -> ShowModelImpl(id = show.id, name = show.name) }
    }

    override suspend fun searchShows(query: String): List<ShowModel> =
        showsRemoteDataSource.getShows(query = query).map { seriesResponse ->
            ShowModelImpl(id = seriesResponse.show.id, name = seriesResponse.show.name)
        }

    private companion object {
        const val SHOWS_PER_PAGE = 250
    }
}
