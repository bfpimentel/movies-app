package dev.pimentel.series.presentation.series.data

sealed class SeriesIntention {

    data class SearchSeries(val query: String?) : SeriesIntention()
}
