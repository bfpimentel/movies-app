package dev.pimentel.series.presentation.shows.data

data class ShowViewData(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val premieredDate: String,
    val status: String,
    val rating: Double
)
