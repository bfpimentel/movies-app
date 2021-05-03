package dev.pimentel.shows.domain.repository

import dev.pimentel.shows.domain.model.ShowsPageModel
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {
    fun getShows(): Flow<ShowsPageModel>
    suspend fun searchShows(query: String)
    suspend fun getMoreShows(nextPage: Int)
    suspend fun favoriteOrRemoveShow(showId: Int)
}
