package com.example.climblogger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sectors")
data class Sector(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "area_uuid") val areaId: String,
    @ColumnInfo(name = "comment") val comment: String?,
    @PrimaryKey
    @ColumnInfo(name = "sector_uuid")
    val sectorId: String
) {
    override fun toString(): String {
        return "$name"
    }
}