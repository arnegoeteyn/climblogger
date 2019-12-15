package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
abstract class SectorDao : BaseDao<Sector>() {

    @Query("SELECT * FROM sectors ORDER BY name")
    abstract fun getAllSectors(): LiveData<List<Sector>>

    @Query("SELECT * FROM sectors WHERE sector_uuid = :sector_id")
    abstract fun getSector(sector_id: String): LiveData<Sector>

    @Query(" SELECT * FROM sectors WHERE area_uuid == :area_id ORDER BY name")
    abstract fun sectorsFromArea(area_id: String): LiveData<List<Sector>>
}