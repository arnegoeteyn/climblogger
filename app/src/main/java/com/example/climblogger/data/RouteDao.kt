package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface RouteDao {

    @Query("SELECT route_id, name, grade from routes")
    fun getAllRoutes(): LiveData<List<Route>>
}