package dev.pimentel.series.data.repository

import dev.pimentel.series.data.model.SeriesModelImpl
import dev.pimentel.series.domain.model.SeriesModel
import dev.pimentel.series.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SeriesRepositoryTest {

    private val exampleLocalDataSource = mockk<ExampleLocalDataSource>()
    private val repository: SeriesRepository = SeriesRepositoryImpl(exampleLocalDataSource)

    @Test
    fun `should get example`() = runBlockingTest {
        val example = "value"
        val seriesModel: SeriesModel = SeriesModelImpl(value = example)

        coEvery { exampleLocalDataSource.getExample() } returns example

        assertEquals(repository.getSeries(), seriesModel)

        coVerify(exactly = 1) { exampleLocalDataSource.getExample() }
        confirmEverythingVerified()
    }

    private fun confirmEverythingVerified() {
        confirmVerified(exampleLocalDataSource)
    }
}
