package dev.pimentel.template.domain.usecase

import dev.pimentel.template.domain.entity.Example
import dev.pimentel.template.domain.repository.ExampleRepository

class GetExample(private val repository: ExampleRepository) : SuspendedUseCase<NoParams, Example> {

    override suspend fun invoke(params: NoParams): Example =
        Example(value = repository.getExample().value)
}
