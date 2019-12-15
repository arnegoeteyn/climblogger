package com.example.climblogger.ui.ascent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddAscentViewModel(application: Application, route_id: String) : AndroidViewModel(application) {

    private val ascentRepository: AscentRepository
    private val routeRepository: RouteRepository

    val route: LiveData<Route>

    val allRoutes: LiveData<List<Route>>

    init {

        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        val routeDao = RouteRoomDatabase.getDatabase(application).routeDao()
        val ascentWithRouteDao = RouteRoomDatabase.getDatabase(application).ascentWithRouteDao()
        ascentRepository = AscentRepository(ascentDao, ascentWithRouteDao)
        routeRepository = RouteRepository(routeDao)
        route = routeRepository.getRoute(route_id)

        allRoutes = routeRepository.allRoutes
    }

    // wrapper function so it gets called on another thread
    fun insertAscent(ascent: Ascent) = viewModelScope.launch(Dispatchers.IO) {
        ascentRepository.insertAscent(ascent)
    }

    fun getAscent(ascent_id: String): LiveData<Ascent> {
        return ascentRepository.getAscent(ascent_id)
    }
}

