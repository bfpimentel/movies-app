package dev.pimentel.shows.presentation.information

import android.content.Context
import dev.pimentel.shows.R
import dev.pimentel.shows.domain.entity.Episode
import dev.pimentel.shows.domain.entity.ShowInformation
import dev.pimentel.shows.presentation.information.data.InformationViewData
import dev.pimentel.shows.presentation.information.mapper.InformationViewDataMapper
import dev.pimentel.shows.presentation.information.mapper.InformationViewDataMapperImpl
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InformationViewDataMapperTest {

    private val context = mockk<Context>(relaxed = true)
    private val informationViewDataMapper: InformationViewDataMapper = InformationViewDataMapperImpl(context)

    @Test
    fun `should map show information to view data`() = runBlockingTest {
        val showInformation = ShowInformation(
            id = 0,
            name = "0",
            summary = "<p>0</p>",
            status = "0",
            premieredDate = "0",
            rating = 0F,
            imageUrl = "0",
            isFavorite = true,
            schedule = ShowInformation.Schedule(
                time = "0", days = listOf("0", "1")
            ),
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

        val informationViewData = InformationViewData(
            name = "0",
            summary = "0",
            status = "0",
            premieredDate = "0",
            rating = 0F,
            imageUrl = "0",
            isFavorite = true,
            schedule = "0 at 0,\n1 at 0",
            seasons = listOf(
                InformationViewData.SeasonViewData(
                    number = 0,
                    episodes = listOf(
                        InformationViewData.SeasonViewData.EpisodeViewData(
                            id = 0,
                            number = 0,
                            name = "0",
                        )
                    ),
                    isOpen = true
                )
            )
        )

        every { context.getString(R.string.information_schedule_day, "0", "0") } returns "0 at 0"
        every { context.getString(R.string.information_schedule_day, "1", "0") } returns "1 at 0"

        assertEquals(informationViewDataMapper.map(showInformation, listOf(0)), informationViewData)

        verify(exactly = 1) {
            context.getString(R.string.information_schedule_day, "0", "0")
            context.getString(R.string.information_schedule_day, "1", "0")
        }
        confirmVerified(context)
    }
}
