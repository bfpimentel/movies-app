package dev.pimentel.series.domain.usecase

import dev.pimentel.series.domain.entity.Example
import dev.pimentel.series.domain.model.ExampleModel
import dev.pimentel.series.domain.repository.ExampleRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetExampleTest {

    private val exampleRepository = mockk<ExampleRepository>()
    private val useCase = GetExample(exampleRepository)

    @Test
    fun `should get example and map it to entity`() = runBlockingTest {
        val exampleModel = object : ExampleModel {
            override val value: String = "value"
        }

        val example = Example(value = "value")

        coEvery { exampleRepository.getExample() } returns exampleModel

        assertEquals(useCase(NoParams), example)

        coVerify(exactly = 1) { exampleRepository.getExample() }
        confirmVerified(exampleRepository)
    }
}
