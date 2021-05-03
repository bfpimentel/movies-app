package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.repository.ShowsRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

class SearchFavoritesTest {

    private val showsRepository = mockk<ShowsRepository>()
    private val searchFavorites = SearchFavorites(showsRepository)

    @Test
    fun `should redirect call to repository`() = runBlockingTest {
        val query = "query"

        coJustRun { showsRepository.searchFavorites(query) }

        searchFavorites(SearchFavorites.Params(query))

        coVerify(exactly = 1) { showsRepository.searchFavorites(query) }
        confirmVerified(showsRepository)
    }
}
