package dev.pimentel.shows.shared.shows

import android.content.Context
import dev.pimentel.shows.R
import dev.pimentel.shows.domain.entity.Show
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test

class ShowViewDataMapperTest {

    private val context = mockk<Context>(relaxed = true)
    private val showViewDataMapper: ShowViewDataMapper = ShowViewDataMapperImpl(context = context)

    @Test
    fun `should map shows to view data`() {
        val shows = listOf(
            Show(
                id = 1,
                name = "name1",
                status = "status1",
                premieredDate = "date1",
                rating = 2F,
                imageUrl = "image1",
                isFavorite = false
            ),
            Show(
                id = 2,
                name = "name2",
                status = "status2",
                premieredDate = null,
                rating = 4F,
                imageUrl = "image2",
                isFavorite = false
            ),
        )

        val showsViewData = listOf(
            ShowViewData(
                id = 1,
                name = "name1",
                status = "status1",
                premieredDate = "date1",
                rating = 1F,
                imageUrl = "image1",
                isFavorite = false
            ),
            ShowViewData(
                id = 2,
                name = "name2",
                status = "status2",
                premieredDate = "Unknown",
                rating = 2F,
                imageUrl = "image2",
                isFavorite = false
            ),
        )

        every { context.getString(R.string.shows_item_unknown_premier_date) } returns "Unknown"

        assertEquals(showViewDataMapper.mapAll(shows), showsViewData)

        verify(exactly = 1) {
            context.getString(R.string.shows_item_unknown_premier_date)
        }
        confirmVerified(context)
    }
}
