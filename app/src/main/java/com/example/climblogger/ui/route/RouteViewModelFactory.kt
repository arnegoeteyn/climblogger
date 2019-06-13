package com.example.climblogger.ui.route

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RouteViewModelFactory(application: Application, route_id: Int) : ViewModelProvider.Factory {

    private val application: Application = application
    private val route_id: Int = route_id

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RouteViewModel(application, route_id) as T
    }
}