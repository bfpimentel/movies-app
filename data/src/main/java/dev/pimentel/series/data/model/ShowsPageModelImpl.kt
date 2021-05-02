package dev.pimentel.series.data.model

import dev.pimentel.series.domain.model.ShowsPageModel

data class ShowsPageModelImpl(
    override val shows: List<ShowModelImpl>,
    override val nextPage: Int
) : ShowsPageModel
