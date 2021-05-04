package dev.pimentel.shows.presentation.information.mapper

import android.content.Context
import dev.pimentel.shows.R
import dev.pimentel.shows.domain.entity.Episode
import dev.pimentel.shows.domain.entity.ShowInformation
import dev.pimentel.shows.presentation.information.data.InformationViewData

interface InformationViewDataMapper {
    suspend fun map(showInformation: ShowInformation, openSeasons: List<Int>): InformationViewData
}

class InformationViewDataMapperImpl(
    private val context: Context
) : InformationViewDataMapper {

    override suspend fun map(showInformation: ShowInformation, openSeasons: List<Int>): InformationViewData =
        InformationViewData(
            name = showInformation.name,
            summary = showInformation.summary
                .replace(PARAGRAPH_OPENING, EMPTY_STRING)
                .replace(PARAGRAPH_CLOSING, EMPTY_STRING),
            status = showInformation.status,
            premieredDate = showInformation.premieredDate
                ?: context.getString(R.string.information_unknown_premier_date),
            rating = (showInformation.rating ?: DEFAULT_RATING) / FIVE_STAR_RATING_DIVIDER,
            imageUrl = showInformation.imageUrl,
            isFavorite = showInformation.isFavorite,
            schedule = showInformation.schedule?.days?.joinToString(SCHEDULE_SEPARATOR) { day ->
                context.getString(R.string.information_schedule_day, day, showInformation.schedule?.time)
            },
            seasons = showInformation.episodes
                .groupBy(Episode::season)
                .map { (seasonNumber, episodes) ->
                    InformationViewData.SeasonViewData(
                        isOpen = openSeasons.contains(seasonNumber),
                        number = seasonNumber,
                        episodes = episodes.map { episode ->
                            InformationViewData.SeasonViewData.EpisodeViewData(
                                id = episode.id,
                                number = episode.number,
                                name = episode.name
                            )
                        }
                    )
                }
        )

    private companion object {
        const val DEFAULT_RATING = 0F
        const val FIVE_STAR_RATING_DIVIDER = 2
        const val SCHEDULE_SEPARATOR = ",\n"
        const val PARAGRAPH_OPENING = "<p>"
        const val PARAGRAPH_CLOSING = "</p>"
        const val EMPTY_STRING = ""
    }
}
