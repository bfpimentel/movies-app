package dev.pimentel.shows.data.model

import dev.pimentel.shows.domain.model.ShowsPageModel

data class ShowsPageModelImpl(
    override val shows: List<ShowModelImpl>,
    override val nextPage: Int
) : ShowsPageModel
