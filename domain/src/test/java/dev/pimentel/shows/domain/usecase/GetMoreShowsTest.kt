package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.repository.ShowsRepository
import dev.pimentel.shows.domain.usecase.GetMoreShows
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

class GetMoreShowsTest {

    private val showsRepository = mockk<ShowsRepository>()
    private val getMoreShows = GetMoreShows(showsRepository)

    @Test
    fun `should redirect call to repository`() = runBlockingTest {
        val nextPage = 0

        coJustRun { showsRepository.getMoreShows(nextPage) }

        getMoreShows(GetMoreShows.Params(nextPage))

        coVerify(exactly = 1) { showsRepository.getMoreShows(nextPage) }
        confirmVerified(showsRepository)
    }
}
