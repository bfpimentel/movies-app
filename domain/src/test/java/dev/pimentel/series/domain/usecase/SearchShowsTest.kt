package dev.pimentel.series.domain.usecase

import dev.pimentel.series.domain.repository.ShowsRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

class SearchShowsTest {

    private val showsRepository = mockk<ShowsRepository>()
    private val searchShows = SearchShows(showsRepository)

    @Test
    fun `should redirect call to repository`() = runBlockingTest {
        val query = "query"

        coJustRun { showsRepository.searchShows(query) }

        searchShows(SearchShows.Params(query))

        coVerify(exactly = 1) { showsRepository.searchShows(query) }
        confirmVerified(showsRepository)
    }
}
