package dev.pimentel.shows.data.repository

import app.cash.turbine.test
import dev.pimentel.shows.data.body.ShowResponseBody
import dev.pimentel.shows.data.body.ShowSearchResponseBody
import dev.pimentel.shows.data.dto.ShowDTO
import dev.pimentel.shows.data.model.ShowModelImpl
import dev.pimentel.shows.data.model.ShowsPageModelImpl
import dev.pimentel.shows.data.sources.local.ShowsLocalDataSource
import dev.pimentel.shows.data.sources.remote.ShowsRemoteDataSource
import dev.pimentel.shows.domain.model.ShowsPageModel
import dev.pimentel.shows.domain.repository.ShowsRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
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
            ShowResponseBody(
                id = 0,
                name = "name0",
                status = "0",
                premieredDate = "0",
                rating = ShowResponseBody.RatingResponseBody(average = 0F),
                image = ShowResponseBody.ImageResponseBody(originalUrl = "0"),
            ),
            ShowResponseBody(
                id = 1,
                name = "name1",
                status = "1",
                premieredDate = "1",
                rating = ShowResponseBody.RatingResponseBody(average = 1F),
                image = ShowResponseBody.ImageResponseBody(originalUrl = "1"),
            ),
        )
        val showsSecondPageResponseBody = listOf(
            ShowResponseBody(
                id = 2,
                name = "name2",
                status = "2",
                premieredDate = "2",
                rating = ShowResponseBody.RatingResponseBody(average = 2F),
                image = ShowResponseBody.ImageResponseBody(originalUrl = "2"),
            ),
            ShowResponseBody(
                id = 3,
                name = "name3",
                status = "3",
                premieredDate = "3",
                rating = ShowResponseBody.RatingResponseBody(average = 3F),
                image = ShowResponseBody.ImageResponseBody(originalUrl = "3"),
            ),
        )

        val favoriteIds = listOf(1, 2)

        val showsFirstPageModel: ShowsPageModel = ShowsPageModelImpl(
            shows = listOf(
                ShowModelImpl(
                    id = 0,
                    name = "name0",
                    status = "0",
                    premieredDate = "0",
                    rating = 0F,
                    imageUrl = "0",
                    isFavorite = false
                ),
                ShowModelImpl(
                    id = 1,
                    name = "name1",
                    status = "1",
                    premieredDate = "1",
                    rating = 1F,
                    imageUrl = "1",
                    isFavorite = true
                ),
            ),
            nextPage = 1
        )
        val showsSecondPageModel: ShowsPageModel = ShowsPageModelImpl(
            shows = listOf(
                ShowModelImpl(
                    id = 0,
                    name = "name0",
                    status = "0",
                    premieredDate = "0",
                    rating = 0F,
                    imageUrl = "0",
                    isFavorite = false
                ),
                ShowModelImpl(
                    id = 1,
                    name = "name1",
                    status = "1",
                    premieredDate = "1",
                    rating = 1F,
                    imageUrl = "1",
                    isFavorite = true
                ),
                ShowModelImpl(
                    id = 2,
                    name = "name2",
                    status = "2",
                    premieredDate = "2",
                    rating = 2F,
                    imageUrl = "2",
                    isFavorite = true
                ),
                ShowModelImpl(
                    id = 3,
                    name = "name3",
                    status = "3",
                    premieredDate = "3",
                    rating = 3F,
                    imageUrl = "3",
                    isFavorite = false
                ),
            ),
            nextPage = 2
        )

        coEvery { showsRemoteDataSource.getShows(page = 0) } returns showsFirstPageResponseBody
        coEvery { showsRemoteDataSource.getShows(page = 1) } returns showsSecondPageResponseBody
        coEvery { showsLocalDataSource.getFavoriteShowsIds() } returns flowOf(favoriteIds)

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
            showsLocalDataSource.getFavoriteShowsIds()
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should search for two distinct values`() = runBlocking {
        val showsFirstSearchResponseBody = listOf(
            ShowSearchResponseBody(
                ShowResponseBody(
                    id = 0,
                    name = "breaking bad",
                    status = "0",
                    premieredDate = "0",
                    rating = ShowResponseBody.RatingResponseBody(average = 0F),
                    image = ShowResponseBody.ImageResponseBody(originalUrl = "0")
                )
            )
        )
        val showsSecondSearchResponseBody = listOf(
            ShowSearchResponseBody(
                ShowResponseBody(
                    id = 1,
                    name = "true detective",
                    status = "1",
                    premieredDate = "1",
                    rating = ShowResponseBody.RatingResponseBody(average = 1F),
                    image = ShowResponseBody.ImageResponseBody(originalUrl = "1")
                )
            )
        )

        val showsFirstPageModel: ShowsPageModel = ShowsPageModelImpl(
            shows = listOf(
                ShowModelImpl(
                    id = 0,
                    name = "breaking bad",
                    status = "0",
                    premieredDate = "0",
                    rating = 0F,
                    imageUrl = "0",
                    isFavorite = false
                )
            ),
            nextPage = 0
        )
        val showsSecondPageModel: ShowsPageModel = ShowsPageModelImpl(
            shows = listOf(
                ShowModelImpl(
                    id = 1,
                    name = "true detective",
                    status = "1",
                    premieredDate = "1",
                    rating = 1F,
                    imageUrl = "1",
                    isFavorite = false
                )
            ),
            nextPage = 0
        )

        coEvery { showsRemoteDataSource.getShows(query = "breaking") } returns showsFirstSearchResponseBody
        coEvery { showsRemoteDataSource.getShows(query = "true") } returns showsSecondSearchResponseBody
        coEvery { showsLocalDataSource.getFavoriteShowsIds() } returns flowOf(emptyList())

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
            showsLocalDataSource.getFavoriteShowsIds()
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should search only one time while on debounce delay`() = runBlocking {
        val showsFirstSearchResponseBody = listOf(
            ShowSearchResponseBody(
                ShowResponseBody(
                    id = 1,
                    name = "true detective",
                    status = "1",
                    premieredDate = "1",
                    rating = ShowResponseBody.RatingResponseBody(average = 1F),
                    image = ShowResponseBody.ImageResponseBody(originalUrl = "1")
                )
            )
        )

        val showsFirstPageModel: ShowsPageModel = ShowsPageModelImpl(
            shows = listOf(
                ShowModelImpl(
                    id = 1,
                    name = "true detective",
                    status = "1",
                    premieredDate = "1",
                    rating = 1F,
                    imageUrl = "1",
                    isFavorite = false
                )
            ),
            nextPage = 0
        )

        coEvery { showsRemoteDataSource.getShows(query = "true") } returns showsFirstSearchResponseBody
        coEvery { showsLocalDataSource.getFavoriteShowsIds() } returns flowOf(emptyList())

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
            showsLocalDataSource.getFavoriteShowsIds()
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should favorite show when it is not already a favorite`() = runBlocking {
        val showId = 0

        val showBody = ShowResponseBody(
            id = 1,
            name = "true detective",
            status = "1",
            premieredDate = "1",
            rating = ShowResponseBody.RatingResponseBody(average = 1F),
            image = ShowResponseBody.ImageResponseBody(originalUrl = "1")
        )

        val showToBeSaved = ShowDTO(
            id = 1,
            name = "true detective",
            status = "1",
            premieredDate = "1",
            rating = 1F,
            imageUrl = "1"
        )

        coEvery { showsLocalDataSource.isFavorite(showId) } returns false
        coEvery { showsRemoteDataSource.getShowInformation(showId) } returns showBody
        coJustRun { showsLocalDataSource.saveFavoriteShow(showToBeSaved) }

        val repository = getRepositoryInstance()

        repository.favoriteOrRemoveShow(showId)

        coVerify(exactly = 1) {
            showsLocalDataSource.isFavorite(showId)
            showsRemoteDataSource.getShowInformation(showId)
            showsLocalDataSource.saveFavoriteShow(showToBeSaved)
        }
        confirmEverythingVerified()
    }

    @Test
    fun `should remove show from favorites when it is already a favorite`() = runBlocking {
        val showId = 0

        coEvery { showsLocalDataSource.isFavorite(showId) } returns true
        coJustRun { showsLocalDataSource.removeShowFromFavorites(showId) }

        val repository = getRepositoryInstance()

        repository.favoriteOrRemoveShow(showId)

        coVerify(exactly = 1) {
            showsLocalDataSource.isFavorite(showId)
            showsLocalDataSource.removeShowFromFavorites(showId)
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
