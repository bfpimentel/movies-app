package dev.pimentel.template.data.sources.local

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExampleLocalDataSourceTest {

    private val dataSource: ExampleLocalDataSource = ExampleLocalDataSource()

    @Test
    fun `should return example`() {
        assertEquals(dataSource.getExample(), "This is an example!")
    }
}
