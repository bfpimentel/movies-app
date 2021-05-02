package dev.pimentel.series.data.repository

import dev.pimentel.series.data.model.ShowModelImpl
import dev.pimentel.series.data.model.ShowsPageModelImpl
import dev.pimentel.series.data.sources.local.ShowsLocalDataSource
import dev.pimentel.series.data.sources.remote.ShowsRemoteDataSource
import dev.pimentel.series.domain.model.ShowsPageModel
import dev.pimentel.series.domain.repository.ShowsRepository
import dev.pimentel.series.domain.usecase.GetShows
import kotlinx.coroutines.flow.*

class ShowsRepositoryImpl(
    private val showsRemoteDataSource: ShowsRemoteDataSource,
    private val showsLocalDataSource: ShowsLocalDataSource
) : ShowsRepository {

    private val getShowsPublisher = MutableStateFlow<Pair<Int, String?>>(Pair(DEFAULT_PAGE, null))

    override fun getShows(): Flow<ShowsPageModel> =
        getShowsPublisher
            .debounce(GET_SHOWS_DEBOUNCE_INTERVAL)
            .mapLatest { (page, query) ->
                val shows = if (query.isNullOrEmpty()) {
                    showsRemoteDataSource.getShows(page = page)
                        .map { show -> ShowModelImpl(id = show.id, name = show.name) }
                } else {
                    showsRemoteDataSource.getShows(query = query)
                        .map { show -> ShowModelImpl(id = show.info.id, name = show.info.name) }
                }

                Triple(page, query, shows)
            }
            .distinctUntilChanged()
            .catch { ShowsPageModelImpl(shows = emptyList(), nextPage = GetShows.NO_MORE_PAGES) }
            .scan(
                ShowsPageModelImpl(
                    shows = emptyList(),
                    nextPage = DEFAULT_PAGE
                )
            ) { accumulator, (page, query, shows) ->
                when {
                    query != null -> ShowsPageModelImpl(shows = shows, nextPage = DEFAULT_PAGE)
                    page == DEFAULT_PAGE -> ShowsPageModelImpl(shows = shows, nextPage = page + 1)
                    else -> ShowsPageModelImpl(shows = accumulator.shows + shows, nextPage = page + 1)
                }
            }

    override suspend fun getMoreShows(nextPage: Int) = getShowsPublisher.emit(Pair(nextPage, null))

    override suspend fun searchShows(query: String) = getShowsPublisher.emit(Pair(DEFAULT_PAGE, query))

    private companion object {
        const val GET_SHOWS_DEBOUNCE_INTERVAL = 1000L
        const val DEFAULT_PAGE = 0
    }
}
