package dev.pimentel.series.domain.repository

import dev.pimentel.series.domain.model.ExampleModel

interface ExampleRepository {
    suspend fun getExample(): ExampleModel
}
