package com.example.climblogger.utils

import com.example.climblogger.data.FakeDatabase
import com.example.climblogger.data.RouteRepository
import com.example.climblogger.ui.main.RoutesViewModelFactory

// Finally a singleton which doesn't need anything passed to the constructor
object InjectorUtils {

    fun provideRoutesViewModelFactory(): RoutesViewModelFactory {
        val routesRepository = RouteRepository.getInstance(FakeDatabase.getInstance().routeDao)
        return RoutesViewModelFactory(routesRepository)
    }
}
