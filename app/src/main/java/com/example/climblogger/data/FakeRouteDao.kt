package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeRouteDao {
    private val routeList = mutableListOf<Route>()
    private val routes = MutableLiveData<List<Route>>()

    init {
        routeList.add(Route("Die Zwei Muskeltieren", "8a"))
        routeList.add(Route("Fatfinger", "8a"))
        routeList.add(Route("Leda", "7a"))
        routes.value = routeList
    }

    fun addRoute(route: Route) {
        routeList.add(route)
        routes.value = routeList
    }

    fun getRoutes() = routes as LiveData<List<Route>>
}
