package dev.pimentel.series.data.repository

import dev.pimentel.series.data.model.ExampleModelImpl
import dev.pimentel.series.data.sources.local.ExampleLocalDataSource
import dev.pimentel.series.domain.model.ExampleModel
import dev.pimentel.series.domain.repository.ExampleRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExampleRepositoryTest {

    private val exampleLocalDataSource = mockk<ExampleLocalDataSource>()
    private val repository: ExampleRepository = ExampleRepositoryImpl(exampleLocalDataSource)

    @Test
    fun `should get example`() = runBlockingTest {
        val example = "value"
        val exampleModel: ExampleModel = ExampleModelImpl(value = example)

        coEvery { exampleLocalDataSource.getExample() } returns example

        assertEquals(repository.getExample(), exampleModel)

        coVerify(exactly = 1) { exampleLocalDataSource.getExample() }
        confirmEverythingVerified()
    }

    private fun confirmEverythingVerified() {
        confirmVerified(exampleLocalDataSource)
    }
}
