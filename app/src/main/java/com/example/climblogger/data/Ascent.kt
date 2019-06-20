package com.example.climblogger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "ascents",
    foreignKeys = [ForeignKey(
        entity = Route::class,
        parentColumns = arrayOf("route_id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class Ascent(
    @ColumnInfo(name = "route_id") val route_id: Int,
    @ColumnInfo(name = "date") val date: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var ascent_id: Int = 0

    override fun toString(): String {
        return "$route_id - $date"
    }
}