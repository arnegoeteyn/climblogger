package com.example.climblogger.data

import androidx.lifecycle.LiveData

class RouteRepository(private val routeDao: RouteDao) {

    val allRoutes: LiveData<List<Route>> = routeDao.getAllRoutes()

}