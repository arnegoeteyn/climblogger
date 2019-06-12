package com.example.climblogger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "routes")
data class Route(
    @PrimaryKey @ColumnInfo(name = "route_id") val route_id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "grade") val grade: String,
    @ColumnInfo(name = "kind") val kind: String,
    @ColumnInfo(name = "comment") val comment: String?,
    @ColumnInfo(name = "link") val link: String?,
    @ColumnInfo(name = "sector_id") val sector_id: Int
) : Serializable {
    override fun toString(): String {
        return "$name - $grade"
    }
}