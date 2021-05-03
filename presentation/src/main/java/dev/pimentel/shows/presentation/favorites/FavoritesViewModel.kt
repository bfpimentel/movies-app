package dev.pimentel.shows.presentation.favorites

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pimentel.shows.domain.usecase.FavoriteOrRemoveShow
import dev.pimentel.shows.presentation.favorites.data.FavoritesIntention
import dev.pimentel.shows.presentation.favorites.data.FavoritesState
import dev.pimentel.shows.shared.dispatchers.DispatchersProvider
import dev.pimentel.shows.shared.mvi.StateViewModelImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
//    private val getFavorites: GetFavorites,
//    private val searchFavorites: SearchFavorites,
    private val favoriteOrRemoveShow: FavoriteOrRemoveShow,
    dispatchersProvider: DispatchersProvider,
    @FavoritesStateQualifier initialState: FavoritesState
) : StateViewModelImpl<FavoritesState, FavoritesIntention>(
    dispatchersProvider = dispatchersProvider,
    initialState = initialState
), FavoritesContract.ViewModel {

    init {
        viewModelScope.launch(dispatchersProvider.io) { }
    }

    override suspend fun handleIntentions(intention: FavoritesIntention) {
        when (intention) {
            is FavoritesIntention.FavoriteOrRemoveShow -> TODO()
            is FavoritesIntention.SearchFavorites -> getFavorites()
        }
    }

    private suspend fun getFavorites() {

    }
}
