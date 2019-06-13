package com.example.climblogger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "ascents")
data class Ascent(
    @PrimaryKey @ColumnInfo(name = "id") val ascent_id: Int,
    @ColumnInfo(name = "route_id") val route_id: Int,
    @ColumnInfo(name = "date") val date: String
) : Serializable {
    override fun toString(): String {
        return "$route_id - $date"
    }
}