package dev.pimentel.shows.domain.repository

import dev.pimentel.shows.domain.model.EpisodeModel
import dev.pimentel.shows.domain.model.ShowInformationModel
import dev.pimentel.shows.domain.model.ShowModel
import dev.pimentel.shows.domain.model.ShowsPageModel
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {
    fun getShows(): Flow<ShowsPageModel>
    fun getFavoriteShows(): Flow<List<ShowModel>>
    suspend fun searchShows(query: String)
    suspend fun getMoreShows(nextPage: Int)
    suspend fun favoriteOrRemoveShow(showId: Int)
    suspend fun searchFavorites(query: String)

    fun getShowInformation(): Flow<ShowInformationModel>
    suspend fun searchShowInformation(showId: Int)

    suspend fun getEpisodeInformation(showId: Int, seasonNumber: Int, episodeNumber: Int): EpisodeModel
}
