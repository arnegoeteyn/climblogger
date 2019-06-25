package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class RouteRepository(private val routeDao: RouteDao) {

    fun getRoute(route_id: String): LiveData<Route> {
        return routeDao.getRoute(route_id)
    }

    @WorkerThread
    fun insertRoute(route: Route) {
        return routeDao.insertRoute(route)
    }

    val allRoutes: LiveData<List<Route>> = routeDao.getAllRoutes()

    @WorkerThread
    suspend fun deleteRoute(route: Route) {
        return routeDao.deleteRoute(route)
    }
}