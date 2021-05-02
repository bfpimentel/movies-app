package dev.pimentel.shows.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.pimentel.shows.data.dto.ShowDTO
import dev.pimentel.shows.data.sources.local.ShowsLocalDataSource

@Database(
    entities = [ShowDTO::class],
    version = 1,
    exportSchema = false
)
abstract class SeriesDatabase : RoomDatabase() {

    abstract fun createShowsLocalDataSource(): ShowsLocalDataSource
}
