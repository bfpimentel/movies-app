package dev.pimentel.template.data.repository

import dev.pimentel.template.data.model.ExampleModelImpl
import dev.pimentel.template.data.sources.local.ExampleLocalDataSource
import dev.pimentel.template.domain.model.ExampleModel
import dev.pimentel.template.domain.repository.ExampleRepository

class ExampleRepositoryImpl(
    private val exampleLocalDataSource: ExampleLocalDataSource
) : ExampleRepository {

    override suspend fun getExample(): ExampleModel = ExampleModelImpl(value = exampleLocalDataSource.getExample())
}
