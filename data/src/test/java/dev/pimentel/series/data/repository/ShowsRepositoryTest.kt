package dev.pimentel.series.data.repository

import app.cash.turbine.test
import dev.pimentel.series.data.body.ShowResponseBody
import dev.pimentel.series.data.body.ShowSearchResponseBody
import dev.pimentel.series.data.model.ShowModelImpl
import dev.pimentel.series.data.model.ShowsPageModelImpl
import dev.pimentel.series.data.sources.local.ShowsLocalDataSource
import dev.pimentel.series.data.sources.remote.ShowsRemoteDataSource
import dev.pimentel.series.domain.model.ShowsPageModel
import dev.pimentel.series.domain.repository.ShowsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.time.seconds

class ShowsRepositoryTest {

    private val showsLocalDataSource = mockk<ShowsLocalDataSource>()
    private val showsRemoteDataSource = mockk<ShowsRemoteDataSource>()

    private val dispatcher = TestCoroutineDispatcher()

    @BeforeEach
    fun `set up dispatcher`() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterEach
    fun `tear down`() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `should get two pages of shows`() = runBlocking {
        val showsFirstPageResponseBody = listOf(
            ShowResponseBody(id = 0, name = "name1"),
            ShowResponseBody(id = 1, name = "name2"),
        )
        val showsSecondPageResponseBody = listOf(
            ShowResponseBody(id = 2, name = "name3"),
            ShowResponseBody(id = 3, name = "name4"),
        )

        val showsFirstPageModel: ShowsPageModel = ShowsPageModelImpl(
            shows = listOf(
                ShowModelImpl(id = 0, name = "name1"),
                ShowModelImpl(id = 1, name = "name2"),
            ),
            nextPage = 1
        )
        val showsSecondPageModel: ShowsPageModel = ShowsPageModelImpl(
            shows = listOf(
                ShowModelImpl(id = 0, name = "name1"),
                ShowModelImpl(id = 1, name = "name2"),
                ShowModelImpl(id = 2, name = "name3"),
                ShowModelImpl(id = 3, name = "name4"),
            ),
            nextPage = 2
        )

        coEvery { showsRemoteDataSource.getShows(page = 0) } returns showsFirstPageResponseBody
        coEvery { showsRemoteDataSource.getShows(page = 1) } returns showsSecondPageResponseBody

        val repository = getRepositoryInstance()

        repository.getShows().test(2.seconds) {
            assertEquals(expectItem(), initialShowsPageValue)

            delay(1.seconds)
            repository.getMoreShows(0)
            assertEquals(expectItem(), showsFirstPageModel)

            delay(1.seconds)
            repository.getMoreShows(1)
            assertEquals(expectItem(), showsSecondPageModel)

            cancel()
        }

        coVerify(exactly = 1) {
            showsRemoteDataSource.getShows(page = 0)
            showsRemoteDataSource.getShows(page = 1)
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should search for two distinct values`() = runBlocking {
        val showsFirstSearchResponseBody = listOf(
            ShowSearchResponseBody(ShowResponseBody(id = 0, name = "breaking bad"))
        )
        val showsSecondSearchResponseBody = listOf(
            ShowSearchResponseBody(ShowResponseBody(id = 1, name = "true detective"))
        )

        val showsFirstPageModel: ShowsPageModel = ShowsPageModelImpl(
            shows = listOf(ShowModelImpl(id = 0, name = "breaking bad")),
            nextPage = 0
        )
        val showsSecondPageModel: ShowsPageModel = ShowsPageModelImpl(
            shows = listOf(ShowModelImpl(id = 1, name = "true detective")),
            nextPage = 0
        )

        coEvery { showsRemoteDataSource.getShows(query = "breaking") } returns showsFirstSearchResponseBody
        coEvery { showsRemoteDataSource.getShows(query = "true") } returns showsSecondSearchResponseBody

        val repository = getRepositoryInstance()

        repository.getShows().test(2.seconds) {
            assertEquals(expectItem(), initialShowsPageValue)

            repository.searchShows("breaking")
            delay(1.seconds)
            assertEquals(expectItem(), showsFirstPageModel)

            repository.searchShows("true")
            delay(1.seconds)
            assertEquals(expectItem(), showsSecondPageModel)

            cancelAndConsumeRemainingEvents()
        }

        coVerify(exactly = 1) {
            showsRemoteDataSource.getShows(query = "breaking")
            showsRemoteDataSource.getShows(query = "true")
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should search for one value only on debounce delay`() = runBlocking {
        val showsFirstSearchResponseBody = listOf(
            ShowSearchResponseBody(ShowResponseBody(id = 1, name = "true detective"))
        )

        val showsFirstPageModel: ShowsPageModel = ShowsPageModelImpl(
            shows = listOf(ShowModelImpl(id = 1, name = "true detective")),
            nextPage = 0
        )

        coEvery { showsRemoteDataSource.getShows(query = "true") } returns showsFirstSearchResponseBody

        val repository = getRepositoryInstance()

        repository.getShows().test(2.seconds) {
            assertEquals(expectItem(), initialShowsPageValue)

            repository.searchShows("breaking")
            repository.searchShows("true")
            delay(1.seconds)
            assertEquals(expectItem(), showsFirstPageModel)

            cancelAndConsumeRemainingEvents()
        }

        coVerify(exactly = 1) {
            showsRemoteDataSource.getShows(query = "true")
        }
        confirmEverythingVerified()
    }

    private fun confirmEverythingVerified() {
        confirmVerified(
            showsRemoteDataSource,
            showsLocalDataSource
        )
    }

    private fun getRepositoryInstance(): ShowsRepository = ShowsRepositoryImpl(
        showsRemoteDataSource = showsRemoteDataSource,
        showsLocalDataSource = showsLocalDataSource
    )

    companion object {
        val initialShowsPageValue = ShowsPageModelImpl(
            shows = emptyList(),
            nextPage = 0
        )
    }
}
