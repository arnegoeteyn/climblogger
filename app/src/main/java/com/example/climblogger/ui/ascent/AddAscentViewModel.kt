package com.example.climblogger.ui.ascent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    // wrapper function so it gets called on another thread
    fun insertAscent(ascent: Ascent) = viewModelScope.launch(Dispatchers.IO){
        ascentRepository.insertAscent(ascent)
    }
}

