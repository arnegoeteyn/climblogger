package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class RouteDao : BaseDao<Route>() {

    @Query("SELECT * from routes ORDER BY grade DESC, name ASC")
    abstract fun getAllRoutes(): LiveData<List<Route>>

    @Query("SELECT * FROM routes WHERE route_uuid == :route_id")
    abstract fun getRoute(route_id: String): LiveData<Route>

    @Query(" SELECT * FROM routes WHERE sector_uuid == :sector_id ORDER BY grade DESC")
    abstract fun routesFromSector(sector_id: String): LiveData<List<Route>>
}