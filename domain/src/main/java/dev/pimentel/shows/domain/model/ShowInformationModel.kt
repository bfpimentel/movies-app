package dev.pimentel.shows.domain.model

interface ShowInformationModel {
    val id: Int
    val name: String
    val summary: String
    val status: String
    val premieredDate: String?
    val rating: Float?
    val imageUrl: String?
    val isFavorite: Boolean
    val schedule: ScheduleModel?
    val episodes: List<EpisodeModel>

    interface ScheduleModel {
        val time: String
        val days: List<String>
    }
}
