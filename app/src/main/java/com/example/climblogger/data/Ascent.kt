package com.example.climblogger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "ascents"
)
data class Ascent(
    @ColumnInfo(name = "route_id") val route_id: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "kind") val kind: String,
    @ColumnInfo(name = "comment") val comment: String?
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var ascent_id: Int = 0

    override fun toString(): String {
        return "$route_id - $date"
    }
}