package com.example.climblogger.data

import androidx.lifecycle.LiveData

class RouteAmountRepository(private val routeAmountDoa: RouteAmountDoa) {

    val sportAmounts: LiveData<List<RouteAmount>> = routeAmountDoa.getRouteAmounts("sport")
    val boulderAmounts: LiveData<List<RouteAmount>> = routeAmountDoa.getRouteAmounts("boulder")
}