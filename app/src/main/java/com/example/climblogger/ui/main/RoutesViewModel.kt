package com.example.climblogger.ui.main

import androidx.lifecycle.ViewModel
import com.example.climblogger.data.RouteRepository

class RoutesViewModel(private val routesRepository: RouteRepository) : ViewModel() {
    fun getRoutes() = routesRepository.getRoutes()
}