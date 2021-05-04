package dev.pimentel.shows.presentation.episode

import app.cash.turbine.test
import dev.pimentel.shows.ViewModelTest
import dev.pimentel.shows.domain.entity.Episode
import dev.pimentel.shows.domain.usecase.GetEpisodeInformation
import dev.pimentel.shows.presentation.episode.data.EpisodeIntention
import dev.pimentel.shows.presentation.episode.data.EpisodeState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EpisodeViewModelTest : ViewModelTest() {

    private val getEpisodeInformation = mockk<GetEpisodeInformation>()

    @Test
    fun `should get episode information and map it to state`() = runBlockingTest {
        val episode = Episode(
            id = 0,
            number = 0,
            season = 0,
            name = "0",
            summary = "0",
            imageUrl = "0",
            airDate = "0",
            airTime = "0"
        )

        val episodeInformationParams = GetEpisodeInformation.Params(0, 0, 0)

        coEvery { getEpisodeInformation(episodeInformationParams) } returns episode

        val viewModel = getViewModelInstance()

        viewModel.state.test {
            assertEquals(expectItem(), initialState)

            viewModel.publish(EpisodeIntention.GetEpisode(0, 0, 0))

            val state = expectItem()
            assertEquals(state.name, episode.name)
            assertEquals(state.airDate, episode.airDate)
            assertEquals(state.airTime, episode.airTime)
            assertEquals(state.summary, episode.summary)
            assertEquals(state.imageUrl, episode.imageUrl)
            assertEquals(state.number, episode.number)
            assertEquals(state.season, episode.season)
        }

        coVerify(exactly = 1) { getEpisodeInformation(episodeInformationParams) }
        confirmVerified(getEpisodeInformation)
    }

    private fun getViewModelInstance(): EpisodeContract.ViewModel = EpisodeViewModel(
        getEpisodeInformation = getEpisodeInformation,
        dispatchersProvider = dispatchersProvider,
        initialState = initialState
    )

    private companion object {
        val initialState = EpisodeState()
    }
}
