package dev.pimentel.shows.presentation.information.data

data class InformationViewData(
    val name: String,
    val summary: String,
    val status: String,
    val premieredDate: String?,
    val rating: Float,
    val imageUrl: String?,
    val isFavorite: Boolean,
    val schedule: String?,
    val seasons: List<SeasonViewData>,
) {

    data class SeasonViewData(
        val number: Int,
        val episodes: List<EpisodeViewData>,
        val isOpen: Boolean
    ) {

        data class EpisodeViewData(
            val id: Int,
            val number: Int,
            val name: String,
        )
    }
}
