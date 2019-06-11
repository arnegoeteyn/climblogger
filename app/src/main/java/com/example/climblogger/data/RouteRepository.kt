package com.example.climblogger.data

class RouteRepository private constructor(private val routeDao: FakeRouteDao) {

    fun addRoute(route: Route) {
        routeDao.addRoute(route)
    }

    fun getRoutes() = routeDao.getRoutes()

    companion object {
        @Volatile
        private var instance: RouteRepository? = null

        fun getInstance(routeDao: FakeRouteDao) =
            instance ?: synchronized(this) {
                instance ?: RouteRepository(routeDao).also { instance = it }
            }
    }
}