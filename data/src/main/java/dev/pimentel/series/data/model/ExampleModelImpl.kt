package dev.pimentel.series.data.model

import dev.pimentel.series.domain.model.ExampleModel

data class ExampleModelImpl(
    override val value: String
) : ExampleModel
