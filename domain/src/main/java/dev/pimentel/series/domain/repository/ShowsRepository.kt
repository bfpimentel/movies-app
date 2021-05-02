package dev.pimentel.series.domain.repository

import dev.pimentel.series.domain.model.ShowsPageModel
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {
    fun getShows(): Flow<ShowsPageModel>
    suspend fun searchShows(query: String)
    suspend fun getMoreShows(nextPage: Int)
}
