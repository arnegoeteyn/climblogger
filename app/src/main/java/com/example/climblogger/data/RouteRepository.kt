package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class RouteRepository(private val routeDao: RouteDao) {

    fun getRoute(route_id: String): LiveData<Route> {
        return routeDao.getRoute(route_id)
    }

    @WorkerThread
    fun insertRoute(route: Route): Long {
        return routeDao.insert(route)
    }

    @WorkerThread
    fun updateRoute(route: Route) {
        return routeDao.update(route)
    }
    
    @WorkerThread
    fun deleteRoute(route: Route) {
        return routeDao.delete(route)
    }

    val allRoutes: LiveData<List<Route>> = routeDao.getAllRoutes()


    fun routesFromSector(sector_id: String): LiveData<List<Route>> {
        return routeDao.routesFromSector(sector_id)
    }
}