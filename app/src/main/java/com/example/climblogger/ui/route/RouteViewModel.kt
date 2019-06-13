package com.example.climblogger.ui.route

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.*

class RouteViewModel(application: Application, route_id: Int) : AndroidViewModel(application) {
    private val ascentRepository: AscentRepository
    private val routeRepository: RouteRepository

    val route: LiveData<Route>
    val routeAscents: LiveData<List<Ascent>>

    init {
        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        val routeDao = RouteRoomDatabase.getDatabase(application).routeDao()
        ascentRepository = AscentRepository(ascentDao)
        routeRepository = RouteRepository(routeDao)
        routeAscents = ascentRepository.loadAscentsFromRoute(route_id)
        route = routeRepository.getRoute(route_id)
    }
}