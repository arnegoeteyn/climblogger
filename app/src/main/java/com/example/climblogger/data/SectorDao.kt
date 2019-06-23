package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface SectorDao {

    @Query("SELECT * FROM sectors ORDER BY name")
    fun getAllSectors(): LiveData<List<Sector>>
}