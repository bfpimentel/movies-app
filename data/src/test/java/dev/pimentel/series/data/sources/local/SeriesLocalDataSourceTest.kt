package dev.pimentel.series.data.sources.local

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SeriesLocalDataSourceTest {

    private val dataSource: ExampleLocalDataSource = ExampleLocalDataSource()

    @Test
    fun `should return example`() {
        assertEquals(dataSource.getExample(), "This is an example!")
    }
}
