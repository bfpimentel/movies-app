package dev.pimentel.series.domain.usecase

import dev.pimentel.series.domain.entity.Example
import dev.pimentel.series.domain.repository.ExampleRepository

class GetExample(private val repository: ExampleRepository) : SuspendedUseCase<NoParams, Example> {

    override suspend fun invoke(params: NoParams): Example =
        Example(value = repository.getExample().value)
}
