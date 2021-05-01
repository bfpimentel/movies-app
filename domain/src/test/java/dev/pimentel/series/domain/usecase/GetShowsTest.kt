package dev.pimentel.series.domain.usecase

import dev.pimentel.series.domain.entity.Show
import dev.pimentel.series.domain.model.ShowModel
import dev.pimentel.series.domain.repository.ShowsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetShowsTest {

    private val showsRepository = mockk<ShowsRepository>()
    private val useCase = GetShows(showsRepository)

    @Test
    fun `should get shows and map them to entities`() = runBlockingTest {
        val showsModels = listOf(
            object : ShowModel {
                override val id: Int = 1
                override val name: String = "name1"
            },
            object : ShowModel {
                override val id: Int = 2
                override val name: String = "name2"
            }
        )

        val shows = listOf(
            Show(id = 1, name = "name1"),
            Show(id = 2, name = "name2"),
        )

        val page = 1

        coEvery { showsRepository.getShows(page = page) } returns showsModels

        assertEquals(useCase(GetShows.Params(page)), shows)

        coVerify(exactly = 1) { showsRepository.getShows(page = page) }
        confirmVerified(showsRepository)
    }
}
