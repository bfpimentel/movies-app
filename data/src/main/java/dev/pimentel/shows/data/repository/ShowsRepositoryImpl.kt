package dev.pimentel.shows.data.repository

import dev.pimentel.shows.data.body.ShowResponseBody
import dev.pimentel.shows.data.body.ShowSearchResponseBody
import dev.pimentel.shows.data.dto.ShowDTO
import dev.pimentel.shows.data.model.ShowModelImpl
import dev.pimentel.shows.data.model.ShowsPageModelImpl
import dev.pimentel.shows.data.sources.local.ShowsLocalDataSource
import dev.pimentel.shows.data.sources.remote.ShowsRemoteDataSource
import dev.pimentel.shows.domain.model.ShowsPageModel
import dev.pimentel.shows.domain.repository.ShowsRepository
import dev.pimentel.shows.domain.usecase.GetShows
import kotlinx.coroutines.flow.*
import retrofit2.HttpException

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

                Triple(page, query, shows)
            }
            .distinctUntilChanged()
            .catch { error ->
                if ((error as? HttpException)?.code() == 404) {
                    emit(Triple(GetShows.NO_MORE_PAGES, null, emptyList()))
                }
            }
            .scan(Pair(emptyList<ShowResponseBody>(), DEFAULT_PAGE)) { (lastShows, _), (page, query, shows) ->
                when {
                    query != null -> Pair(shows, DEFAULT_PAGE)
                    page == DEFAULT_PAGE -> Pair(shows, page + NEXT_PAGE_MODIFIER)
                    else -> Pair(lastShows + shows, page + NEXT_PAGE_MODIFIER)
                }
            }.combine(showsLocalDataSource.getFavoriteShowsIds()) { (shows, nextPage), favoriteIds ->
                ShowsPageModelImpl(shows = shows.mapAllToModel(favoriteIds), nextPage = nextPage)
            }

    override suspend fun getMoreShows(nextPage: Int) = getShowsPublisher.emit(Pair(nextPage, null))

    override suspend fun searchShows(query: String) = getShowsPublisher.emit(Pair(DEFAULT_PAGE, query))

    override suspend fun favoriteOrRemoveShow(showId: Int) {
        if (showsLocalDataSource.getFavoriteById(showId) == null) {
            showsLocalDataSource.saveFavoriteShow(
                ShowDTO(id = showId, name = "Placeholder") // TODO: Get show details from endpoint and then save it
            )
        } else {
            showsLocalDataSource.removeShowFromFavorites(showId)
        }
    }

    private fun List<ShowResponseBody>.mapAllToModel(favoriteIds: List<Int>) = map { show ->
        ShowModelImpl(
            id = show.id,
            name = show.name,
            status = show.status,
            premieredDate = show.premieredDate ?: "unknown",
            rating = show.rating.average,
            imageUrl = show.image?.originalUrl.orEmpty(),
            isFavorite = favoriteIds.contains(show.id)
        )
    }

    private fun ShowResponseBody.mapToDTO(): ShowDTO = let { body ->
        ShowDTO(
            id = body.id,
            name = body.name,
        )
    }

    private companion object {
        const val GET_SHOWS_DEBOUNCE_INTERVAL = 1000L
        const val DEFAULT_PAGE = 0
        const val NEXT_PAGE_MODIFIER = 1
    }
}
