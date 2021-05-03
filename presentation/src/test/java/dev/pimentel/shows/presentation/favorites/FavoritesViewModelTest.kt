package dev.pimentel.shows.presentation.favorites

import dev.pimentel.shows.ViewModelTest
import dev.pimentel.shows.domain.entity.Show
import dev.pimentel.shows.domain.usecase.FavoriteOrRemoveShow
import dev.pimentel.shows.domain.usecase.GetFavorites
import dev.pimentel.shows.domain.usecase.NoParams
import dev.pimentel.shows.domain.usecase.SearchFavorites
import dev.pimentel.shows.presentation.favorites.data.FavoritesIntention
import dev.pimentel.shows.presentation.favorites.data.FavoritesState
import dev.pimentel.shows.shared.navigator.NavigatorRouter
import dev.pimentel.shows.shared.shows.ShowViewData
import dev.pimentel.shows.shared.shows.ShowViewDataMapper
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FavoritesViewModelTest : ViewModelTest() {

    private val navigator = mockk<NavigatorRouter>()
    private val getFavorites = mockk<GetFavorites>()
    private val searchFavorites = mockk<SearchFavorites>()
    private val favoriteOrRemoveShow = mockk<FavoriteOrRemoveShow>()
    private val showViewDataMapper = mockk<ShowViewDataMapper>()

    @Test
    fun `should get favorites`() = runBlockingTest {
        val favorites = listOf(
            Show(
                id = 1,
                name = "name1",
                status = "status1",
                premieredDate = "date1",
                rating = 2F,
                imageUrl = "image1",
                isFavorite = false
            ),
            Show(
                id = 2,
                name = "name2",
                status = "status2",
                premieredDate = "date2",
                rating = 4F,
                imageUrl = "image2",
                isFavorite = false
            ),
        )

        val showsViewData = listOf(
            ShowViewData(
                id = 1,
                name = "name1",
                status = "status1",
                premieredDate = "date1",
                rating = 1F,
                imageUrl = "image1",
                isFavorite = false
            ),
            ShowViewData(
                id = 2,
                name = "name2",
                status = "status2",
                premieredDate = "date2",
                rating = 2F,
                imageUrl = "image2",
                isFavorite = false
            ),
        )

        val viewModel = getViewModelInstance {
            coEvery { getFavorites(NoParams) } returns flowOf(favorites)
            coEvery { showViewDataMapper.mapAll(favorites) } returns showsViewData
        }

        val favoritesStateValues = arrayListOf<FavoritesState>()
        val favoritesStateJob = launch { viewModel.state.toList(favoritesStateValues) }

        val firstShowsState = favoritesStateValues[0]
        assertEquals(firstShowsState.showsEvent!!.value, showsViewData)

        coVerify(exactly = 1) {
            getFavorites(NoParams)
            showViewDataMapper.mapAll(favorites)
        }
        confirmEverythingVerified()

        favoritesStateJob.cancel()
    }

    @Test
    fun `should search shows`() = runBlockingTest {
        val query = "query"

        val searchFavoritesParams = SearchFavorites.Params(query)

        coJustRun { searchFavorites(searchFavoritesParams) }

        val viewModel = getViewModelInstance()

        viewModel.publish(FavoritesIntention.SearchFavorites(query))

        coVerify(exactly = 1) {
            getFavorites(NoParams)
            searchFavorites(searchFavoritesParams)
            showViewDataMapper.mapAll(emptyList())
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should favorite or remove show`() = runBlockingTest {
        val showId = 1

        val favoriteOrRemoveShowParams = FavoriteOrRemoveShow.Params(showId)

        coJustRun { favoriteOrRemoveShow(favoriteOrRemoveShowParams) }

        val viewModel = getViewModelInstance()

        viewModel.publish(FavoritesIntention.FavoriteOrRemoveShow(showId))

        coVerify(exactly = 1) {
            getFavorites(NoParams)
            favoriteOrRemoveShow(favoriteOrRemoveShowParams)
            showViewDataMapper.mapAll(emptyList())
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should navigate to information`() = runBlockingTest {
        val showId = 1

        val directions = FavoritesFragmentDirections.toInformationFragment(showId)

        coJustRun { navigator.navigate(directions) }

        val viewModel = getViewModelInstance()

        viewModel.publish(FavoritesIntention.NavigateToInformation(showId))

        coVerify(exactly = 1) {
            getFavorites(NoParams)
            showViewDataMapper.mapAll(emptyList())
            navigator.navigate(directions)
        }
        confirmEverythingVerified()
    }

    private fun getViewModelInstance(doBefore: (() -> Unit)? = null): FavoritesContract.ViewModel {
        doBefore
            ?.invoke()
            ?: run {
                coEvery { getFavorites(NoParams) } returns flowOf(emptyList())
                coEvery { showViewDataMapper.mapAll(emptyList()) } returns emptyList()
            }

        return FavoritesViewModel(
            navigator = navigator,
            getFavorites = getFavorites,
            searchFavorites = searchFavorites,
            favoriteOrRemoveShow = favoriteOrRemoveShow,
            showViewDataMapper = showViewDataMapper,
            dispatchersProvider = dispatchersProvider,
            initialState = initialState
        )
    }

    private fun confirmEverythingVerified() {
        confirmVerified(
            navigator,
            getFavorites,
            searchFavorites,
            favoriteOrRemoveShow,
            showViewDataMapper
        )
    }

    private companion object {
        val initialState = FavoritesState()
    }
}
