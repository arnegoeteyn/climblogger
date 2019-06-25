package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RouteDao {

    @Query("SELECT * from routes ORDER BY grade DESC")
    fun getAllRoutes(): LiveData<List<Route>>

    @Query("SELECT * FROM routes WHERE route_id == :route_id")
    fun getRoute(route_id: Int): LiveData<Route>

    @Insert
    fun insertRoute(route: Route)

    @Delete
    fun deleteRoute(route: Route)
}