package dev.pimentel.series.data.model

import dev.pimentel.series.domain.model.SeriesModel

data class SeriesModelImpl(
    override val id: Int,
    override val name: String
) : SeriesModel
