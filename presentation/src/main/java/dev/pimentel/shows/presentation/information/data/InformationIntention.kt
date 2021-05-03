package dev.pimentel.shows.presentation.information.data

sealed class InformationIntention {

    data class SearchShowInformation(val showId: Int) : InformationIntention()

    object FavoriteOrRemoveShow : InformationIntention()
}
