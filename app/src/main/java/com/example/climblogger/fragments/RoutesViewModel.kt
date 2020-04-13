package com.example.climblogger.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.*

class RoutesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RouteRepository

    val allRoutes: LiveData<List<RouteWithAscents>>

    init {
        val routeDao = RouteRoomDatabase.getDatabase(application).routeDao()
        repository = RouteRepository(routeDao)

        allRoutes = repository.routesWithAscents()
    }
}