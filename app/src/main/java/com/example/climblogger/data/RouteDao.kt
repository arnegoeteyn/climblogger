package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RouteDao {

    @Query("SELECT * from routes ORDER BY grade DESC, name ASC")
    fun getAllRoutes(): LiveData<List<Route>>

    @Query("SELECT * FROM routes WHERE route_uuid == :route_id")
    fun getRoute(route_id: String): LiveData<Route>

    @Insert
    fun insertRoute(route: Route)

    @Delete
    fun deleteRoute(route: Route)

    @Query(" SELECT * FROM routes WHERE sector_uuid == :sector_id ORDER BY grade DESC")
    fun routesFromSector(sector_id: String): LiveData<List<Route>>
}