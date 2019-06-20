package com.example.climblogger.ui.route

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.Route
import com.example.climblogger.data.RouteRepository
import com.example.climblogger.data.RouteRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddRouteViewModel(application: Application) : AndroidViewModel(application) {
    private val routeRepository: RouteRepository

    init {
        val routeDao = RouteRoomDatabase.getDatabase(application).routeDao()
        routeRepository = RouteRepository(routeDao)
    }

    fun insertRoute(route: Route) = viewModelScope.launch(Dispatchers.IO) {
        routeRepository.insertRoute(route)
    }
}