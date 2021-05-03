package dev.pimentel.shows.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Shows")
data class ShowDTO(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "premieredDate") val premieredDate: String?,
    @ColumnInfo(name = "rating") val rating: Float?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
)
