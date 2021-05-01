package dev.pimentel.series.presentation.shows

import dev.pimentel.series.ViewModelTest
import dev.pimentel.series.domain.entity.Show
import dev.pimentel.series.domain.usecase.GetShows
import dev.pimentel.series.domain.usecase.NoParams
import dev.pimentel.series.presentation.shows.data.ShowsIntention
import dev.pimentel.series.presentation.shows.data.ShowsState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ShowsViewModelTest : ViewModelTest() {

    private val getExample = mockk<GetShows>()

    @Test
    fun `should get example`() = runBlockingTest {
        val example = Show(value = "value")

        coEvery { getExample(NoParams) } returns example

        val viewModel = getViewModelInstance()

        val exampleStateValues = arrayListOf<ShowsState>()
        val exampleStateJob = launch { viewModel.state.toList(exampleStateValues) }

        viewModel.publish(ShowsIntention.SearchShows)

        val firstExampleState = exampleStateValues[0]
        assertEquals(firstExampleState, initialState)
        val secondExampleState = exampleStateValues[1]
        assertEquals(secondExampleState.example, example.value)

        coVerify(exactly = 1) { getExample(NoParams) }
        confirmEverythingVerified()

        exampleStateJob.cancel()
    }

    private fun getViewModelInstance(): ShowsContract.ViewModel =
        ShowsViewModel(
            dispatchersProvider = dispatchersProvider,
            getExample = getExample,
            initialState = initialState
        )

    private fun confirmEverythingVerified() {
        confirmVerified(getExample)
    }

    private companion object {
        val initialState = ShowsState()
    }
}
