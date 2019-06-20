package com.example.climblogger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "routes"
)
data class Route(
    @ColumnInfo(name = "sector_id") val sector_id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "grade") val grade: String,
    @ColumnInfo(name = "kind") val kind: String,
    @ColumnInfo(name = "comment") val comment: String?,
    @ColumnInfo(name = "link") val link: String?,
    @ColumnInfo(name = "pitch") val pitch: Int?,
    @ColumnInfo(name = "multipitch_id") val multipitch_id: Int?

) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "route_id")
    var route_id: Int = 0

    override fun toString(): String {
        return "$name - $grade"
    }
}