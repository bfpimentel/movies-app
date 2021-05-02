package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.repository.ShowsRepository
import dev.pimentel.shows.domain.usecase.SearchShows
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
