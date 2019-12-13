package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.*

data class RouteWithSector(
    @Embedded
    val route: Route,
    val sector_name: String
)


@Dao
interface RouteWithSectorDao {

    @Query(
        """
                select routes.*, sectors.name as sector_name from routes left join sectors USING(sector_uuid)
                WHERE route_uuid == :route_uuid
                ORDER BY grade desc, name asc
            """
    )
    /*
        Returns a route with the name of the sector the route lies in
     */
    abstract fun getRouteWithSector(route_uuid: String): LiveData<RouteWithSector>
}


class RouteWithSectorRepository(private val routeWithSectorDoa: RouteWithSectorDao) {

    /*
        Returns the route with the sector it lies in
     */
    fun getRoute(route_id: String): LiveData<RouteWithSector> {
        return routeWithSectorDoa.getRouteWithSector(route_id)
    }
}

