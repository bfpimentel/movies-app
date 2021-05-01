package dev.pimentel.series.data.repository

import dev.pimentel.series.data.body.ShowResponseBody
import dev.pimentel.series.data.dto.ShowDTO
import dev.pimentel.series.data.model.ShowModelImpl
import dev.pimentel.series.data.sources.local.ShowsLocalDataSource
import dev.pimentel.series.data.sources.remote.ShowsRemoteDataSource
import dev.pimentel.series.domain.model.ShowModel
import dev.pimentel.series.domain.repository.ShowsRepository
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ShowsRepositoryTest {

    private val showsLocalDataSource = mockk<ShowsLocalDataSource>()
    private val showsRemoteDataSource = mockk<ShowsRemoteDataSource>()
    private val repository: ShowsRepository
        get() = ShowsRepositoryImpl(
            showsRemoteDataSource = showsRemoteDataSource,
            showsLocalDataSource = showsLocalDataSource
        )

    @Test
    fun `should get shows from remote server when last saved page is smaller than requested one`() = runBlockingTest {
        val requestedPage = 0

        val showsResponseBody = listOf(
            ShowResponseBody(id = 0, name = "name1"),
            ShowResponseBody(id = 1, name = "name2"),
        )

        val showsToBeSaved = listOf(
            ShowDTO(id = 0, name = "name1"),
            ShowDTO(id = 1, name = "name2"),
        )

        val savedShowsForRequestedPage = listOf(
            ShowDTO(id = 0, name = "name1"),
            ShowDTO(id = 1, name = "name2"),
        )

        val showsModels: List<ShowModel> = listOf(
            ShowModelImpl(id = 0, name = "name1"),
            ShowModelImpl(id = 1, name = "name2"),
        )

        coEvery { showsLocalDataSource.getLastShowId() } returns 0
        coEvery { showsRemoteDataSource.getShows(page = requestedPage) } returns showsResponseBody
        coJustRun { showsLocalDataSource.saveShows(shows = showsToBeSaved) }
        coEvery { showsLocalDataSource.getShows(page = requestedPage) } returns savedShowsForRequestedPage

        assertEquals(repository.getShows(page = requestedPage), showsModels)

        coVerify(exactly = 1) {
            showsLocalDataSource.getLastShowId()
            showsRemoteDataSource.getShows(page = requestedPage)
            showsLocalDataSource.saveShows(shows = showsToBeSaved)
            showsLocalDataSource.getShows(page = requestedPage)
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should get shows from local source when last saved page is bigger than requested one`() = runBlockingTest {
        val requestedPage = 0

        val showsForRequestedPage = listOf(
            ShowDTO(id = 0, name = "name1"),
            ShowDTO(id = 1, name = "name2"),
        )

        val showsModels: List<ShowModel> = listOf(
            ShowModelImpl(id = 0, name = "name1"),
            ShowModelImpl(id = 1, name = "name2"),
        )

        coEvery { showsLocalDataSource.getLastShowId() } returns 1000
        coEvery { showsLocalDataSource.getShows(page = requestedPage) } returns showsForRequestedPage

        assertEquals(repository.getShows(page = requestedPage), showsModels)

        coVerify(exactly = 1) {
            showsLocalDataSource.getLastShowId()
        }
        coVerify(exactly = 1) {
            showsLocalDataSource.getLastShowId()
            showsLocalDataSource.getShows(page = requestedPage)
        }
        confirmEverythingVerified()
    }

    private fun confirmEverythingVerified() {
        confirmVerified(
            showsRemoteDataSource,
            showsLocalDataSource
        )
    }
}
