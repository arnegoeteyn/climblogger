package com.example.climblogger.ui.ascent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.climblogger.data.AscentRepository
import com.example.climblogger.data.Route
import com.example.climblogger.data.RouteRepository
import com.example.climblogger.data.RouteRoomDatabase

class AddAscentViewModel(application: Application, route_id: Int) : AndroidViewModel(application) {
    private val ascentRepository: AscentRepository
    private val routeRepository: RouteRepository

    val route: LiveData<Route>

    init {

        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        val routeDao = RouteRoomDatabase.getDatabase(application).routeDao()
        ascentRepository = AscentRepository(ascentDao)
        routeRepository = RouteRepository(routeDao)
        route = routeRepository.getRoute(route_id)
    }
}

