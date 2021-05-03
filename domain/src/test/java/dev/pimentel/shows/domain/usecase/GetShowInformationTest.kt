package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.entity.Episode
import dev.pimentel.shows.domain.entity.ShowInformation
import dev.pimentel.shows.domain.model.EpisodeModel
import dev.pimentel.shows.domain.model.ShowInformationModel
import dev.pimentel.shows.domain.repository.ShowsRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetShowInformationTest {

    private val showsRepository = mockk<ShowsRepository>()
    private val getShowInformation = GetShowInformation(showsRepository)

    @Test
    fun `should get show information and map it to entity`() = runBlockingTest {
        val showInfoModel = object : ShowInformationModel {
            override val id: Int = 0
            override val name: String = "0"
            override val summary: String = "0"
            override val status: String = "0"
            override val premieredDate: String = "0"
            override val rating: Float = 0F
            override val imageUrl: String = "0"
            override val isFavorite: Boolean = true
            override val schedule: ShowInformationModel.ScheduleModel = object : ShowInformationModel.ScheduleModel {
                override val time: String = "0"
                override val days: List<String> = listOf("0")
            }
            override val episodes: List<EpisodeModel> = listOf(
                object : EpisodeModel {
                    override val id: Int = 0
                    override val number: Int = 0
                    override val season: Int = 0
                    override val name: String = "0"
                    override val summary: String = "0"
                    override val imageUrl: String = "0"
                    override val airDate: String = "0"
                    override val airTime: String = "0"
                }
            )
        }

        val showInformation = ShowInformation(
            id = 0,
            name = "0",
            summary = "0",
            status = "0",
            premieredDate = "0",
            rating = 0F,
            imageUrl = "0",
            isFavorite = true,
            schedule = ShowInformation.Schedule(time = "0", days = listOf("0")),
            episodes = listOf(
                Episode(
                    id = 0,
                    number = 0,
                    season = 0,
                    name = "0",
                    summary = "0",
                    imageUrl = "0",
                    airDate = "0",
                    airTime = "0"
                )
            )
        )

        every { showsRepository.getShowInformation() } returns flowOf(showInfoModel)

        assertEquals(getShowInformation(NoParams).first(), showInformation)

        verify(exactly = 1) { showsRepository.getShowInformation() }
        confirmVerified(showsRepository)
    }
}
