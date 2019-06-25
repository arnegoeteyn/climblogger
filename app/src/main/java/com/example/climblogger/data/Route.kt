package com.example.climblogger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "routes"
)
data class Route(
    @ColumnInfo(name = "sector_uuid") val sector_id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "grade") val grade: String,
    @ColumnInfo(name = "kind") val kind: String,
    @ColumnInfo(name = "comment") val comment: String?,
    @ColumnInfo(name = "link") val link: String?,
    @ColumnInfo(name = "pitch") val pitch: Int?,
    @ColumnInfo(name = "multipitch_id") val multipitch_id: Int?,
    @PrimaryKey
    @ColumnInfo(name = "route_uuid")
    val route_id: String

) {

    override fun toString(): String {
        return "$name - $grade"
    }
}