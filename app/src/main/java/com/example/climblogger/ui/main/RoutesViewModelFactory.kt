package com.example.climblogger.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.climblogger.data.RouteRepository

class RoutesViewModelFactory(private val routesRepository: RouteRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RoutesViewModel(routesRepository) as T
    }
}