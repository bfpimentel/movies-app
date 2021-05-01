package dev.pimentel.series.domain.repository

import dev.pimentel.series.domain.model.ShowsModel
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {
    fun getSeries(): Flow<List<ShowsModel>>
    suspend fun getMoreSeries(nextPage: Int)
    suspend fun searchSeries(query: String?): List<ShowsModel>
}
