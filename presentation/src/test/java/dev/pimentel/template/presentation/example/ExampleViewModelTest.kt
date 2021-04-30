package dev.pimentel.template.presentation.example

import dev.pimentel.template.ViewModelTest
import dev.pimentel.template.domain.entity.Example
import dev.pimentel.template.domain.usecase.GetExample
import dev.pimentel.template.domain.usecase.NoParams
import dev.pimentel.template.presentation.example.data.ExampleIntention
import dev.pimentel.template.presentation.example.data.ExampleState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExampleViewModelTest : ViewModelTest() {

    private val getExample = mockk<GetExample>()

    @Test
    fun `should get example`() = runBlockingTest {
        val example = Example(value = "value")

        coEvery { getExample(NoParams) } returns example

        val viewModel = getViewModelInstance()

        val exampleStateValues = arrayListOf<ExampleState>()
        val exampleStateJob = launch { viewModel.state.toList(exampleStateValues) }

        viewModel.publish(ExampleIntention.GetExample)

        val firstExampleState = exampleStateValues[0]
        assertEquals(firstExampleState, initialState)
        val secondExampleState = exampleStateValues[1]
        assertEquals(secondExampleState.example, example.value)

        coVerify(exactly = 1) { getExample(NoParams) }
        confirmEverythingVerified()

        exampleStateJob.cancel()
    }

    private fun getViewModelInstance(): ExampleContract.ViewModel =
        ExampleViewModel(
            dispatchersProvider = dispatchersProvider,
            getExample = getExample,
            initialState = initialState
        )

    private fun confirmEverythingVerified() {
        confirmVerified(getExample)
    }

    private companion object {
        val initialState = ExampleState()
    }
}
