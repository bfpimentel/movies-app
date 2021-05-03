package dev.pimentel.shows.presentation.shows

import dev.pimentel.shows.ViewModelTest
import dev.pimentel.shows.domain.entity.Show
import dev.pimentel.shows.domain.entity.ShowsPage
import dev.pimentel.shows.domain.usecase.*
import dev.pimentel.shows.presentation.shows.data.ShowViewData
import dev.pimentel.shows.presentation.shows.data.ShowsIntention
import dev.pimentel.shows.presentation.shows.data.ShowsState
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ShowsViewModelTest : ViewModelTest() {

    private val favoriteOrRemoveShow = mockk<FavoriteOrRemoveShow>()
    private val getShows = mockk<GetShows>()
    private val getMoreShows = mockk<GetMoreShows>()
    private val searchShows = mockk<SearchShows>()

    @Test
    fun `should get shows`() = runBlockingTest {
        val showsPage = ShowsPage(
            shows = listOf(
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
            ),
            nextPage = 1
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
            coEvery { getShows(NoParams) } returns flowOf(showsPage)
        }

        val showsStateValues = arrayListOf<ShowsState>()
        val showsStateJob = launch { viewModel.state.toList(showsStateValues) }

        val firstShowsState = showsStateValues[0]
        assertEquals(firstShowsState.showsEvent!!.value, showsViewData)

        coVerify(exactly = 1) { getShows(NoParams) }
        confirmEverythingVerified()

        showsStateJob.cancel()
    }

    @Test
    fun `should get more shows`() = runBlockingTest {
        val viewModel = getViewModelInstance()

        val getMoreShowsParams = GetMoreShows.Params(1)

        coJustRun { getMoreShows(getMoreShowsParams) }

        viewModel.publish(ShowsIntention.GetMoreShows)

        coVerify(exactly = 1) {
            getShows(NoParams)
            getMoreShows(getMoreShowsParams)
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should not get more shows when there are no more pages`() = runBlockingTest {
        val showsPage = ShowsPage(shows = emptyList(), nextPage = GetShows.NO_MORE_PAGES)

        val viewModel = getViewModelInstance { coEvery { getShows(NoParams) } returns flowOf(showsPage) }

        viewModel.publish(ShowsIntention.GetMoreShows)

        coVerify(exactly = 1) {
            getShows(NoParams)
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should search shows`() = runBlockingTest {
        val query = "query"

        val viewModel = getViewModelInstance()

        val searchShowsParams = SearchShows.Params(query)

        coJustRun { searchShows(searchShowsParams) }

        viewModel.publish(ShowsIntention.SearchShows(query))

        coVerify(exactly = 1) {
            getShows(NoParams)
            searchShows(searchShowsParams)
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should favorite or remove show`() = runBlockingTest {
        val showId = 1

        val viewModel = getViewModelInstance()

        val favoriteOrRemoveShowParams = FavoriteOrRemoveShow.Params(showId)

        coJustRun { favoriteOrRemoveShow(favoriteOrRemoveShowParams) }

        viewModel.publish(ShowsIntention.FavoriteOrRemoveShow(showId))

        coVerify(exactly = 1) {
            getShows(NoParams)
            favoriteOrRemoveShow(favoriteOrRemoveShowParams)
        }
        confirmEverythingVerified()
    }

    private fun getViewModelInstance(
        doBefore: (() -> Unit)? = null
    ): ShowsContract.ViewModel {
        doBefore
            ?.invoke()
            ?: run { coEvery { getShows(NoParams) } returns flowOf(ShowsPage(shows = emptyList(), nextPage = 1)) }

        return ShowsViewModel(
            dispatchersProvider = dispatchersProvider,
            getShows = getShows,
            getMoreShows = getMoreShows,
            searchShows = searchShows,
            favoriteOrRemoveShow = favoriteOrRemoveShow,
            initialState = initialState
        )
    }

    private fun confirmEverythingVerified() {
        confirmVerified(
            getShows,
            getMoreShows,
            searchShows,
            favoriteOrRemoveShow
        )
    }

    private companion object {
        val initialState = ShowsState()
    }
}
