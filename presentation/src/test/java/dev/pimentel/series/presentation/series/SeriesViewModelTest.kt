package dev.pimentel.series.presentation.series

import dev.pimentel.series.ViewModelTest
import dev.pimentel.series.domain.entity.Series
import dev.pimentel.series.domain.usecase.GetSeries
import dev.pimentel.series.domain.usecase.NoParams
import dev.pimentel.series.presentation.series.data.SeriesIntention
import dev.pimentel.series.presentation.series.data.SeriesState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SeriesViewModelTest : ViewModelTest() {

    private val getExample = mockk<GetSeries>()

    @Test
    fun `should get example`() = runBlockingTest {
        val example = Series(value = "value")

        coEvery { getExample(NoParams) } returns example

        val viewModel = getViewModelInstance()

        val exampleStateValues = arrayListOf<SeriesState>()
        val exampleStateJob = launch { viewModel.state.toList(exampleStateValues) }

        viewModel.publish(SeriesIntention.SearchSeries)

        val firstExampleState = exampleStateValues[0]
        assertEquals(firstExampleState, initialState)
        val secondExampleState = exampleStateValues[1]
        assertEquals(secondExampleState.example, example.value)

        coVerify(exactly = 1) { getExample(NoParams) }
        confirmEverythingVerified()

        exampleStateJob.cancel()
    }

    private fun getViewModelInstance(): SeriesContract.ViewModel =
        SeriesViewModel(
            dispatchersProvider = dispatchersProvider,
            getExample = getExample,
            initialState = initialState
        )

    private fun confirmEverythingVerified() {
        confirmVerified(getExample)
    }

    private companion object {
        val initialState = SeriesState()
    }
}
