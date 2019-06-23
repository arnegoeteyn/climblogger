package com.example.climblogger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sectors")
data class Sector(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "crag_id") val cragId: Int,
    @ColumnInfo(name = "comment") val comment: String?
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sector_id")
    var ascent_id: Int = 0
}