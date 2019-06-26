package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SectorDao {

    @Query("SELECT * FROM sectors ORDER BY name")
    fun getAllSectors(): LiveData<List<Sector>>

    @Insert
    fun insertSector(sector: Sector)

    @Query("SELECT * FROM sectors WHERE sector_uuid = :sector_id")
    fun getSector(sector_id: String): LiveData<Sector>

}