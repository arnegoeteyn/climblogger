package com.example.climblogger.ui.route

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RouteViewModelFactory(private val application: Application, private val route_id: String) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RouteViewModel(application, route_id) as T
    }
}