package dev.pimentel.series.data.repository

import dev.pimentel.series.data.model.ExampleModelImpl
import dev.pimentel.series.data.sources.local.ExampleLocalDataSource
import dev.pimentel.series.domain.model.ExampleModel
import dev.pimentel.series.domain.repository.ExampleRepository

class ExampleRepositoryImpl(
    private val exampleLocalDataSource: ExampleLocalDataSource
) : ExampleRepository {

    override suspend fun getExample(): ExampleModel = ExampleModelImpl(value = exampleLocalDataSource.getExample())
}
