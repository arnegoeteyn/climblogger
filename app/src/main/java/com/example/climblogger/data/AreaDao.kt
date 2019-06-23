package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface AreaDao {
    @Query("SELECT * FROM crags ORDER BY name")
    fun getAllAreas(): LiveData<List<Area>>
}