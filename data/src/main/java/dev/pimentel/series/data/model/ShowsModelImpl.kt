package dev.pimentel.series.data.model

import dev.pimentel.series.domain.model.ShowsModel

data class ShowsModelImpl(
    override val id: Int,
    override val name: String
) : ShowsModel
