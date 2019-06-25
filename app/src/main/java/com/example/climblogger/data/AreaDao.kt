package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AreaDao {
    @Query("SELECT * FROM areas ORDER BY name")
    fun getAllAreas(): LiveData<List<Area>>

    @Insert
    fun insertArea(area: Area)
}