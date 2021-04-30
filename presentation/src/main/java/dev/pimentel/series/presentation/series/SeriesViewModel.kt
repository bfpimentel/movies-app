package dev.pimentel.series.presentation.series

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pimentel.series.domain.usecase.GetSeries
import dev.pimentel.series.domain.usecase.NoParams
import dev.pimentel.series.domain.usecase.SearchSeries
import dev.pimentel.series.presentation.series.data.SeriesIntention
import dev.pimentel.series.presentation.series.data.SeriesState
import dev.pimentel.series.shared.dispatchers.DispatchersProvider
import dev.pimentel.series.shared.mvi.StateViewModelImpl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val getSeries: GetSeries,
    private val searchSeries: SearchSeries,
    dispatchersProvider: DispatchersProvider,
    @WelcomeStateQualifier initialState: SeriesState
) : StateViewModelImpl<SeriesState, SeriesIntention>(
    dispatchersProvider = dispatchersProvider,
    initialState = initialState
), SeriesContract.ViewModel {

    init {
        viewModelScope.launch(dispatchersProvider.io) { getSeries() }
    }

    override suspend fun handleIntentions(intention: SeriesIntention) {
        when (intention) {
            is SeriesIntention.SearchSeries -> searchSeries(intention.query)
        }
    }

    private suspend fun getSeries() {
        getSeries(NoParams).collect { series ->
            Log.d("SERIES", series.toString())
//            updateState { copy(example = example.value) }
        }
    }

    private suspend fun searchSeries(query: String?) {
        searchSeries(SearchSeries.Params(query))
    }
}
