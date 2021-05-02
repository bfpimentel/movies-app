package dev.pimentel.shows.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.pimentel.shows.data.dto.ShowDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowsLocalDataSource {

    @Insert
    suspend fun saveFavoriteShow(show: ShowDTO)

    @Query("DELETE FROM Shows where id = :showId")
    suspend fun removeShowFromFavorites(showId: Int)

    @Query("SELECT id FROM Shows")
    fun getFavoriteShowsIds(): Flow<List<Int>>

    @Query("SELECT * FROM Shows")
    suspend fun getFavoriteShows(): List<ShowDTO>
}
