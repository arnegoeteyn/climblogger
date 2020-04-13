package com.example.climblogger.ui.route

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteViewModel(application: Application, route_id: String) : AndroidViewModel(application) {

    private val ascentRepository: AscentRepository
    private val routeRepository: RouteRepository

    val route: LiveData<RouteWithSector?>
    val routeAscents: LiveData<List<Ascent>>

    init {
        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        val routeDao = RouteRoomDatabase.getDatabase(application).routeDao()

        ascentRepository = AscentRepository(ascentDao)
        routeRepository = RouteRepository(routeDao)

        routeAscents = ascentRepository.loadAscentsFromRoute(route_id)
        route = routeRepository.getRouteWithSector(route_id)
    }


    fun deleteRoute(route: Route) = viewModelScope.launch(Dispatchers.IO) {
        routeRepository.deleteRoute(route)
    }

}