package com.example.climblogger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "ascents",
    foreignKeys = [ForeignKey(
        entity = Route::class,
        parentColumns = arrayOf("route_uuid"),
        childColumns = arrayOf("route_uuid"),
        onDelete = CASCADE
    )]
)
data class Ascent(
    @ColumnInfo(name = "route_uuid") val route_id: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "kind") val kind: String,
    @ColumnInfo(name = "comment") val comment: String?,
    @PrimaryKey
    @ColumnInfo(name = "ascent_uuid")
    val ascent_id: String
) {
    override fun toString(): String {
        return "$route_id - $date"
    }
}