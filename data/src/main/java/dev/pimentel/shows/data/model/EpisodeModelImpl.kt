package dev.pimentel.shows.data.model

import dev.pimentel.shows.domain.model.EpisodeModel

data class EpisodeModelImpl(
    override val id: Int,
    override val number: Int,
    override val season: Int,
    override val name: String,
    override val summary: String,
    override val imageUrl: String?,
    override val airDate: String,
    override val airTime: String
) : EpisodeModel
