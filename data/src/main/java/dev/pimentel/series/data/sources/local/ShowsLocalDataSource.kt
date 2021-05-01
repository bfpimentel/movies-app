package dev.pimentel.series.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.pimentel.series.data.dto.ShowDTO

@Dao
interface ShowsLocalDataSource {

    @Insert
    suspend fun saveShows(shows: List<ShowDTO>)

    @Query(
        """
        SELECT id, name 
        FROM Shows
        WHERE id > (:page * 250)
        LIMIT 250
        """
    )
    suspend fun getShows(page: Int): List<ShowDTO>

    @Query(
        """
        SELECT id 
        FROM Shows
        ORDER BY id DESC
        LIMIT 1
        """
    )
    suspend fun getLastShowId(): Int
}
