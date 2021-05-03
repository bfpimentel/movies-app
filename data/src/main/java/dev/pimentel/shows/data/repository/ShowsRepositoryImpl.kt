package dev.pimentel.shows.data.repository

import dev.pimentel.shows.data.body.ShowResponseBody
import dev.pimentel.shows.data.body.ShowSearchResponseBody
import dev.pimentel.shows.data.dto.ShowDTO
import dev.pimentel.shows.data.model.ShowInformationModelImpl
import dev.pimentel.shows.data.model.ShowModelImpl
import dev.pimentel.shows.data.model.ShowsPageModelImpl
import dev.pimentel.shows.data.sources.local.ShowsLocalDataSource
import dev.pimentel.shows.data.sources.remote.ShowsRemoteDataSource
import dev.pimentel.shows.domain.model.ShowInformationModel
import dev.pimentel.shows.domain.model.ShowModel
import dev.pimentel.shows.domain.model.ShowsPageModel
import dev.pimentel.shows.domain.repository.ShowsRepository
import dev.pimentel.shows.domain.usecase.GetShows
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.scan
import retrofit2.HttpException

class ShowsRepositoryImpl(
    private val showsRemoteDataSource: ShowsRemoteDataSource,
    private val showsLocalDataSource: ShowsLocalDataSource
) : ShowsRepository {

    private val getShowsPublisher = MutableSharedFlow<Pair<Int, String?>>()
    private val favoriteSearchPublisher = MutableSharedFlow<String>()
    private val getShowInformationPublisher = MutableSharedFlow<Int>()

    override fun getShows(): Flow<ShowsPageModel> =
        getShowsPublisher
            .debounce(GET_SHOWS_DEBOUNCE_INTERVAL)
            .mapLatest { (page, query) ->
                val shows = if (query.isNullOrEmpty()) showsRemoteDataSource.getShows(page = page)
                else showsRemoteDataSource.getShows(query = query).map(ShowSearchResponseBody::info)
                Triple(page, query, shows)
            }
            .distinctUntilChanged()
            .catch { error ->
                if ((error as? HttpException)?.code() == NO_MORE_PAGES_CODE) {
                    emit(Triple(GetShows.NO_MORE_PAGES, null, emptyList()))
                }
            }
            .scan(Pair(emptyList<ShowResponseBody>(), DEFAULT_PAGE)) { (lastShows, _), (page, query, shows) ->
                when {
                    query != null -> Pair(shows, DEFAULT_PAGE)
                    page == DEFAULT_PAGE -> Pair(shows, page + NEXT_PAGE_MODIFIER)
                    page == GetShows.NO_MORE_PAGES -> Pair(lastShows, page)
                    else -> Pair(lastShows + shows, page + NEXT_PAGE_MODIFIER)
                }
            }
            .combine(showsLocalDataSource.getFavoriteShowsIds()) { (shows, nextPage), favoriteIds ->
                ShowsPageModelImpl(shows = shows.mapAllToModel(favoriteIds), nextPage = nextPage)
            }

    override fun getFavoriteShows(): Flow<List<ShowModel>> = favoriteSearchPublisher.flatMapLatest { query ->
        showsLocalDataSource.getFavoriteShows(query).map { shows -> shows.mapAllToModel() }
    }

    override suspend fun getMoreShows(nextPage: Int) = getShowsPublisher.emit(Pair(nextPage, null))

    override suspend fun searchShows(query: String) = getShowsPublisher.emit(Pair(DEFAULT_PAGE, query))

    override suspend fun favoriteOrRemoveShow(showId: Int) {
        if (showsLocalDataSource.isFavorite(showId)) {
            showsLocalDataSource.removeShowFromFavorites(showId)
            return
        }

        val newFavoriteShow = showsRemoteDataSource.getShowInformation(showId).mapToDTO()
        showsLocalDataSource.saveFavoriteShow(newFavoriteShow)
    }

    override suspend fun searchFavorites(query: String) = favoriteSearchPublisher.emit(query)

    override fun getShowInformation(): Flow<ShowInformationModel> =
        getShowInformationPublisher
            .mapLatest(showsRemoteDataSource::getShowInformation)
            .combine(showsLocalDataSource.getFavoriteShowsIds()) { response, favoriteIds ->
                response.mapToInfoModel(favoriteIds)
            }

    override suspend fun searchShowInformation(showId: Int) = getShowInformationPublisher.emit(showId)

    private fun List<ShowResponseBody>.mapAllToModel(favoriteIds: List<Int>) = map { show ->
        ShowModelImpl(
            id = show.id,
            name = show.name,
            status = show.status,
            premieredDate = show.premieredDate,
            rating = show.rating.average,
            imageUrl = show.image?.originalUrl,
            isFavorite = favoriteIds.contains(show.id)
        )
    }

    private fun List<ShowDTO>.mapAllToModel(): List<ShowModel> = map { show ->
        ShowModelImpl(
            id = show.id,
            name = show.name,
            status = show.status,
            premieredDate = show.premieredDate,
            rating = show.rating,
            imageUrl = show.imageUrl,
            isFavorite = true
        )
    }

    private fun ShowResponseBody.mapToDTO(): ShowDTO = let { show ->
        ShowDTO(
            id = show.id,
            name = show.name,
            status = show.status,
            premieredDate = show.premieredDate,
            rating = show.rating.average,
            imageUrl = show.image?.originalUrl
        )
    }

    private fun ShowResponseBody.mapToInfoModel(favoriteIds: List<Int>): ShowInformationModel = let { showInfo ->
        ShowInformationModelImpl(
            id = showInfo.id,
            name = showInfo.name,
            status = showInfo.status,
            summary = showInfo.summary,
            premieredDate = showInfo.premieredDate,
            rating = showInfo.rating.average,
            imageUrl = showInfo.image?.originalUrl,
            isFavorite = favoriteIds.contains(showInfo.id),
            schedule = showInfo.schedule?.let { schedule ->
                ShowInformationModelImpl.ScheduleModelImpl(
                    time = schedule.time,
                    days = schedule.days
                )
            },
            episodes = showInfo.embedded!!.episodes.map { episode ->
                ShowInformationModelImpl.EpisodeModelImpl(
                    id = episode.id,
                    number = episode.number,
                    season = episode.season,
                    name = episode.name,
                    summary = episode.summary,
                    imageUrl = episode.image?.originalUrl,
                    airDate = episode.airDate,
                    airTime = episode.airTime
                )
            }
        )
    }

    private companion object {
        const val GET_SHOWS_DEBOUNCE_INTERVAL = 1000L
        const val DEFAULT_PAGE = 0
        const val NEXT_PAGE_MODIFIER = 1
        const val NO_MORE_PAGES_CODE = 404
    }
}
