package com.example.climblogger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "sectors",
    foreignKeys = [ForeignKey(
        entity = Area::class,
        parentColumns = arrayOf("area_uuid"),
        childColumns = arrayOf("area_uuid"),
        onDelete = CASCADE
    )]
)
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