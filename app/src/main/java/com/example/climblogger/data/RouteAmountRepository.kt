package com.example.climblogger.data

import androidx.lifecycle.LiveData

class RouteAmountRepository(private val routeAmountDoa: RouteAmountDoa) {

    val routeAmounts: LiveData<List<RouteAmount>> = routeAmountDoa.getRouteAmounts()
}