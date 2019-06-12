package com.example.climblogger.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.Route
import com.example.climblogger.data.RouteRepository
import com.example.climblogger.data.RouteRoomDatabase

class RoutesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RouteRepository

    val allRoutes: LiveData<List<Route>>

    init {
        val routeDao = RouteRoomDatabase.getDatabase(application).routeDao()
        repository = RouteRepository(routeDao)

        allRoutes = repository.allRoutes
    }
}