package dev.pimentel.shows.data.repository

import dev.pimentel.shows.data.body.ShowResponseBody
import dev.pimentel.shows.data.body.ShowSearchResponseBody
import dev.pimentel.shows.data.model.ShowModelImpl
import dev.pimentel.shows.data.model.ShowsPageModelImpl
import dev.pimentel.shows.data.sources.local.ShowsLocalDataSource
import dev.pimentel.shows.data.sources.remote.ShowsRemoteDataSource
import dev.pimentel.shows.domain.model.ShowsPageModel
import dev.pimentel.shows.domain.repository.ShowsRepository
import dev.pimentel.shows.domain.usecase.GetShows
import kotlinx.coroutines.flow.*

class ShowsRepositoryImpl(
    private val showsRemoteDataSource: ShowsRemoteDataSource,
    private val showsLocalDataSource: ShowsLocalDataSource
) : ShowsRepository {

    private val getShowsPublisher = MutableSharedFlow<Pair<Int, String?>>()

    override fun getShows(): Flow<ShowsPageModel> =
        getShowsPublisher
            .debounce(GET_SHOWS_DEBOUNCE_INTERVAL)
            .mapLatest { (page, query) ->
                val shows = if (query == null) showsRemoteDataSource.getShows(page = page)
                else showsRemoteDataSource.getShows(query = query).map(ShowSearchResponseBody::info)

                Triple(page, query, shows.mapAllToModel())
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
                    page == DEFAULT_PAGE -> ShowsPageModelImpl(shows = shows, nextPage = page + NEXT_PAGE_MODIFIER)
                    else -> ShowsPageModelImpl(shows = accumulator.shows + shows, nextPage = page + NEXT_PAGE_MODIFIER)
                }
            }

    override suspend fun getMoreShows(nextPage: Int) = getShowsPublisher.emit(Pair(nextPage, null))

    override suspend fun searchShows(query: String) = getShowsPublisher.emit(Pair(DEFAULT_PAGE, query))

    private fun List<ShowResponseBody>.mapAllToModel() = map { show ->
        ShowModelImpl(
            id = show.id,
            name = show.name,
            status = show.status,
            premieredDate = show.premieredDate,
            rating = show.rating.average ?: 0F,
            imageUrl = show.image.originalUrl
        )
    }

    private companion object {
        const val GET_SHOWS_DEBOUNCE_INTERVAL = 1000L
        const val DEFAULT_PAGE = 0
        const val NEXT_PAGE_MODIFIER = 1
    }
}
