package dev.pimentel.series.domain.repository

import dev.pimentel.series.domain.model.SeriesModel
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    fun getSeries(): Flow<List<SeriesModel>>
    suspend fun searchSeries(query: String?)
}
