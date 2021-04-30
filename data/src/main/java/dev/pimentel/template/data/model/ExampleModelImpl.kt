package dev.pimentel.template.data.model

import dev.pimentel.template.domain.model.ExampleModel

data class ExampleModelImpl(
    override val value: String
) : ExampleModel
