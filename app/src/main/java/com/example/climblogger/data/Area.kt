package com.example.climblogger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crags")
data class Area(
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "name") val name: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "crag_id")
    var areaId: Int = 0

}