package dev.pimentel.shows.data.model

import dev.pimentel.shows.domain.model.ShowInformationModel

data class ShowInformationModelImpl(
    override val id: Int,
    override val name: String,
    override val summary: String,
    override val status: String,
    override val premieredDate: String?,
    override val rating: Float?,
    override val imageUrl: String?,
    override val isFavorite: Boolean,
    override val schedule: ScheduleModelImpl?,
    override val episodes: List<EpisodeModelImpl>
) : ShowInformationModel {

    data class ScheduleModelImpl(
        override val time: String,
        override val days: List<String>
    ) : ShowInformationModel.ScheduleModel

}
