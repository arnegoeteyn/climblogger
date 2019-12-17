package com.example.climblogger.ui.main.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.*

class RoutesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RouteWithAscentsRepository

    val allRoutes: LiveData<List<RouteWithAscents>>

    init {
        val routeDao = RouteRoomDatabase.getDatabase(application).routeWithAscentsDao()
        repository = RouteWithAscentsRepository(routeDao)

        allRoutes = repository.routesWithAscents()
    }
}