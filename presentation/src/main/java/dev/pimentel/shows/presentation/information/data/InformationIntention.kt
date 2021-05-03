package dev.pimentel.shows.presentation.information.data

sealed class InformationIntention {
    data class SearchShowInformation(val showId: Int) : InformationIntention()
    data class OpenOrCloseSeason(val seasonNumber: Int) : InformationIntention()
    object FavoriteOrRemoveShow : InformationIntention()
}
