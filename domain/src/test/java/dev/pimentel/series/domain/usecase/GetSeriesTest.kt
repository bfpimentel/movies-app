package dev.pimentel.series.domain.usecase

import dev.pimentel.series.domain.entity.Series
import dev.pimentel.series.domain.model.ShowsModel
import dev.pimentel.series.domain.repository.ShowsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetSeriesTest {

    private val exampleRepository = mockk<ShowsRepository>()
    private val useCase = GetSeries(exampleRepository)

    @Test
    fun `should get example and map it to entity`() = runBlockingTest {
        val exampleModel = object : ShowsModel {
            override val value: String = "value"
        }

        val example = Series(value = "value")

        coEvery { exampleRepository.getSeries() } returns exampleModel

        assertEquals(useCase(NoParams), example)

        coVerify(exactly = 1) { exampleRepository.getSeries() }
        confirmVerified(exampleRepository)
    }
}
