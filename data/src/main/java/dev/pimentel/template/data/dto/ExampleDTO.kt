package dev.pimentel.template.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Examples")
data class ExampleDTO(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "value") val value: String
)
