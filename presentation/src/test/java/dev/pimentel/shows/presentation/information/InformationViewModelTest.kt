package dev.pimentel.shows.presentation.information

import app.cash.turbine.test
import dev.pimentel.shows.ViewModelTest
import dev.pimentel.shows.domain.entity.ShowInformation
import dev.pimentel.shows.domain.usecase.FavoriteOrRemoveShow
import dev.pimentel.shows.domain.usecase.GetShowInformation
import dev.pimentel.shows.domain.usecase.NoParams
import dev.pimentel.shows.domain.usecase.SearchShowInformation
import dev.pimentel.shows.presentation.information.data.InformationIntention
import dev.pimentel.shows.presentation.information.data.InformationState
import dev.pimentel.shows.presentation.information.data.InformationViewData
import dev.pimentel.shows.presentation.information.mapper.InformationViewDataMapper
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InformationViewModelTest : ViewModelTest() {

    private val getShowInformation = mockk<GetShowInformation>()
    private val searchShowInformation = mockk<SearchShowInformation>()
    private val favoriteOrRemoveShow = mockk<FavoriteOrRemoveShow>()
    private val informationViewDataMapper = mockk<InformationViewDataMapper>()

    @Test
    fun `should get show information`() = runBlockingTest {
        val viewModel = getViewModelInstance()

        viewModel.state.test {
            assertEquals(expectItem().informationEvent!!.value, informationViewData)

            cancel()
        }

        coVerify(exactly = 1) {
            getShowInformation(NoParams)
            informationViewDataMapper.map(showInformation, emptyList())
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should search show information`() = runBlockingTest {
        val showId = 0

        val searchShowInformationParams = SearchShowInformation.Params(showId)

        coJustRun { searchShowInformation(searchShowInformationParams) }

        val viewModel = getViewModelInstance()

        viewModel.publish(InformationIntention.SearchShowInformation(showId))

        coVerify(exactly = 1) {
            getShowInformation(NoParams)
            informationViewDataMapper.map(showInformation, emptyList())
            searchShowInformation(searchShowInformationParams)
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should favorite or remove show`() = runBlockingTest {
        val showId = 0

        val searchShowInformationParams = SearchShowInformation.Params(showId)
        val favoriteOrRemoveShowParams = FavoriteOrRemoveShow.Params(showId)

        coJustRun { searchShowInformation(searchShowInformationParams) }
        coJustRun { favoriteOrRemoveShow(favoriteOrRemoveShowParams) }

        val viewModel = getViewModelInstance()

        viewModel.publish(InformationIntention.SearchShowInformation(showId))
        viewModel.publish(InformationIntention.FavoriteOrRemoveShow)

        coVerify(exactly = 1) {
            getShowInformation(NoParams)
            informationViewDataMapper.map(showInformation, emptyList())
            searchShowInformation(searchShowInformationParams)
            favoriteOrRemoveShow(favoriteOrRemoveShowParams)
        }
        confirmEverythingVerified()
    }

    // Test never finishes, there is some problem with the runBlockingTest that hasn't been fixed for more than 1 year.
    // Even tried using Turbine, but in this case I couldn't make it work
//    @Test
//    fun `should get new information data when opening a season`() = runBlocking(dispatchersProvider.io) {
//        val seasonNumber = 0
//
//        val newInformationViewData = InformationViewData(
//            name = "0",
//            summary = "0",
//            status = "0",
//            premieredDate = "0",
//            rating = 0F,
//            imageUrl = "0",
//            isFavorite = true,
//            schedule = "0 at 0",
//            seasons = listOf(
//                InformationViewData.SeasonViewData(
//                    number = 0,
//                    episodes = listOf(
//                        InformationViewData.SeasonViewData.EpisodeViewData(
//                            id = 0,
//                            number = 0,
//                            name = "0",
//                        )
//                    ),
//                    isOpen = true
//                )
//            )
//        )
//
//        coEvery { informationViewDataMapper.map(showInformation, listOf(seasonNumber)) } returns newInformationViewData
//
//        val viewModel = getViewModelInstance()
//
//        viewModel.state.test {
//            assertEquals(expectItem().informationEvent!!.value, informationViewData)
//
//            viewModel.publish(InformationIntention.OpenOrCloseSeason(seasonNumber))
//            assertEquals(expectItem().informationEvent!!.value, newInformationViewData)
//
//            cancel()
//        }
//
//        coVerify(exactly = 1) {
//            getShowInformation(NoParams)
//            informationViewDataMapper.map(showInformation, emptyList())
//            informationViewDataMapper.map(showInformation, listOf(seasonNumber))
//        }
//        confirmEverythingVerified()
//    }

    private fun getViewModelInstance(doBefore: (() -> Unit)? = null): InformationContract.ViewModel {
        doBefore
            ?.invoke()
            ?: run {
                coEvery { getShowInformation(NoParams) } returns flowOf(showInformation)
                coEvery { informationViewDataMapper.map(showInformation, emptyList()) } returns informationViewData
            }

        return InformationViewModel(
            getShowInformation = getShowInformation,
            searchShowInformation = searchShowInformation,
            favoriteOrRemoveShow = favoriteOrRemoveShow,
            informationViewDataMapper = informationViewDataMapper,
            dispatchersProvider = dispatchersProvider,
            initialState = initialState
        )
    }

    private fun confirmEverythingVerified() {
        confirmVerified(
            getShowInformation,
            searchShowInformation,
            favoriteOrRemoveShow,
            informationViewDataMapper,
        )
    }

    private companion object {
        val initialState = InformationState()

        val showInformation = ShowInformation(
            id = 0,
            name = "0",
            summary = "0",
            status = "0",
            premieredDate = "0",
            rating = 0F,
            imageUrl = "0",
            isFavorite = true,
            schedule = ShowInformation.Schedule(time = "0", days = listOf("0")),
            episodes = listOf(
                ShowInformation.Episode(
                    id = 0,
                    number = 0,
                    season = 0,
                    name = "0",
                    summary = "0",
                    imageUrl = "0",
                    airDate = "0",
                    airTime = "0"
                )
            )
        )

        val informationViewData = InformationViewData(
            name = "0",
            summary = "0",
            status = "0",
            premieredDate = "0",
            rating = 0F,
            imageUrl = "0",
            isFavorite = true,
            schedule = "0 at 0",
            seasons = listOf(
                InformationViewData.SeasonViewData(
                    number = 0,
                    episodes = listOf(
                        InformationViewData.SeasonViewData.EpisodeViewData(
                            id = 0,
                            number = 0,
                            name = "0",
                        )
                    ),
                    isOpen = false
                )
            )
        )
    }
}
