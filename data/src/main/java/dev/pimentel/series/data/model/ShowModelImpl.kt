package dev.pimentel.series.data.model

import dev.pimentel.series.domain.model.ShowModel

data class ShowModelImpl(
    override val id: Int,
    override val name: String,
    override val status: String,
    override val premieredDate: String,
    override val rating: Float?,
    override val imageUrl: String,
) : ShowModel
