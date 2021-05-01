package dev.pimentel.series.data.model

import dev.pimentel.series.domain.model.ShowModel

data class ShowModelImpl(
    override val id: Int,
    override val name: String
) : ShowModel
