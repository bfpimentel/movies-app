package dev.pimentel.shows.domain.model

interface ShowModel {
    val id: Int
    val name: String
    val status: String
    val premieredDate: String
    val rating: Float?
    val imageUrl: String
}
