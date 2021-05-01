package dev.pimentel.series.domain.repository

import dev.pimentel.series.domain.model.ShowModel

interface ShowsRepository {
    suspend fun getShows(page: Int): List<ShowModel>
    suspend fun searchShows(query: String): List<ShowModel>
}
