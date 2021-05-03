package dev.pimentel.shows.shared.shows

import android.content.Context
import dev.pimentel.shows.domain.entity.Show

interface ShowViewDataMapper {
    fun mapAll(shows: List<Show>): List<ShowViewData>
}

class ShowViewDataMapperImpl(private val context: Context) : ShowViewDataMapper {

    override fun mapAll(shows: List<Show>): List<ShowViewData> = shows.map { show ->
        ShowViewData(
            id = show.id,
            imageUrl = show.imageUrl,
            name = show.name,
            premieredDate = show.premieredDate ?: "Unknown", // TODO: Get string from resources
            status = show.status,
            rating = (show.rating ?: DEFAULT_RATING) / FIVE_STAR_RATING_DIVIDER,
            isFavorite = show.isFavorite
        )
    }

    private companion object {
        const val DEFAULT_RATING = 0F
        const val FIVE_STAR_RATING_DIVIDER = 2
    }
}
