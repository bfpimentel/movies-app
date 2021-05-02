package dev.pimentel.shows.data.model

import dev.pimentel.shows.domain.model.ShowModel

data class ShowModelImpl(
    override val id: Int,
    override val name: String,
    override val status: String,
    override val premieredDate: String,
    override val rating: Float?,
    override val imageUrl: String,
    override val isFavorite: Boolean
) : ShowModel
