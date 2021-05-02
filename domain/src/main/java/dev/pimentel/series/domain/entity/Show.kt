package dev.pimentel.series.domain.entity

data class Show(
    val id: Int,
    val name: String,
    val status: String,
    val premieredDate: String,
    val rating: Float?,
    val imageUrl: String
)
