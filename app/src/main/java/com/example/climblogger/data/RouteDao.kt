package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface RouteDao {

    @Query("SELECT * from routes ORDER BY grade DESC")
    fun getAllRoutes(): LiveData<List<Route>>
}