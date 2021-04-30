package dev.pimentel.template.domain.repository

import dev.pimentel.template.domain.model.ExampleModel

interface ExampleRepository {
    suspend fun getExample(): ExampleModel
}
