package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.entity.Episode
import dev.pimentel.shows.domain.model.EpisodeModel
import dev.pimentel.shows.domain.repository.ShowsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetEpisodeInformationTest {

    private val showsRepository = mockk<ShowsRepository>()
    private val getEpisodeInformation = GetEpisodeInformation(showsRepository)

    @Test
    fun `should get episode information and map it to entity`() = runBlockingTest {
        val episodeModel = object : EpisodeModel {
            override val id: Int = 0
            override val number: Int = 0
            override val season: Int = 0
            override val name: String = "0"
            override val summary: String = "0"
            override val imageUrl: String = "0"
            override val airDate: String = "0"
            override val airTime: String = "0"
        }

        val episode = Episode(
            id = 0,
            number = 0,
            season = 0,
            name = "0",
            summary = "0",
            imageUrl = "0",
            airDate = "0",
            airTime = "0"
        )

        coEvery { showsRepository.getEpisodeInformation(0, 0, 0) } returns episodeModel

        assertEquals(getEpisodeInformation(GetEpisodeInformation.Params(0, 0, 0)), episode)

        coVerify(exactly = 1) { showsRepository.getEpisodeInformation(0, 0, 0) }
        confirmVerified(showsRepository)
    }
}
