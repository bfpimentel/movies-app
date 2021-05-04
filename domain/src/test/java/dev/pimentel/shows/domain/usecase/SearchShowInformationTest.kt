package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.repository.ShowsRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

class SearchShowInformationTest {

    private val showsRepository = mockk<ShowsRepository>()
    private val searchShowInformation = SearchShowInformation(showsRepository)

    @Test
    fun `should redirect call to repository`() = runBlockingTest {
        val showId = 0

        coJustRun { showsRepository.searchShowInformation(showId) }

        searchShowInformation(SearchShowInformation.Params(showId))

        coVerify(exactly = 1) { showsRepository.searchShowInformation(showId) }
        confirmVerified(showsRepository)
    }
}
