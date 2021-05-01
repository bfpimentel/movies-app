package dev.pimentel.series.data.repository

import dev.pimentel.series.data.model.ShowsModelImpl
import dev.pimentel.series.domain.model.ShowsModel
import dev.pimentel.series.domain.repository.ShowsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ShowsRepositoryTest {

    private val exampleLocalDataSource = mockk<ExampleLocalDataSource>()
    private val repository: ShowsRepository = ShowsRepositoryImpl(exampleLocalDataSource)

    @Test
    fun `should get example`() = runBlockingTest {
        val example = "value"
        val showsModel: ShowsModel = ShowsModelImpl(value = example)

        coEvery { exampleLocalDataSource.getExample() } returns example

        assertEquals(repository.getSeries(), showsModel)

        coVerify(exactly = 1) { exampleLocalDataSource.getExample() }
        confirmEverythingVerified()
    }

    private fun confirmEverythingVerified() {
        confirmVerified(exampleLocalDataSource)
    }
}
