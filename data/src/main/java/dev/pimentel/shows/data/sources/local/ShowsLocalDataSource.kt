package dev.pimentel.shows.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.pimentel.shows.data.dto.ShowDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowsLocalDataSource {

    @Query(
        """
        SELECT * 
        FROM Shows 
        WHERE name LIKE '%' || :query || '%'
        ORDER BY name ASC
        """
    )
    fun getFavoriteShows(query: String): Flow<List<ShowDTO>>

    @Query(
        """
        SELECT id 
        FROM Shows
        """
    )
    fun getFavoriteShowsIds(): Flow<List<Int>>

    @Insert
    suspend fun saveFavoriteShow(show: ShowDTO)

    @Query(
        """
        DELETE FROM Shows 
        WHERE id = :showId
        """
    )
    suspend fun removeShowFromFavorites(showId: Int)

    @Query(
        """
        SELECT EXISTS (
            SELECT 1
            FROM Shows 
            WHERE id == :showId
        )
        """
    )
    suspend fun isFavorite(showId: Int): Boolean
}
