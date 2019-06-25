package com.example.climblogger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "areas")
data class Area(
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "name") val name: String,

    @PrimaryKey
    @ColumnInfo(name = "area_uuid")
    val areaId: String
)