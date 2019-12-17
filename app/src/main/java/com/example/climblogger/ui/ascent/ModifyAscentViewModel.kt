package com.example.climblogger.ui.ascent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModifyAscentViewModel(application: Application) :
    AndroidViewModel(application) {

    private val ascentRepository: AscentRepository
    private val routeRepository: RouteRepository

    val allRoutes: LiveData<List<Route>>

    init {

        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        val routeDao = RouteRoomDatabase.getDatabase(application).routeDao()
        val ascentWithRouteDao = RouteRoomDatabase.getDatabase(application).ascentWithRouteDao()
        ascentRepository = AscentRepository(ascentDao, ascentWithRouteDao)
        routeRepository = RouteRepository(routeDao)

        allRoutes = routeRepository.allRoutes
    }

    // wrapper function so it gets called on another thread
    fun insertAscent(ascent: Ascent) = viewModelScope.launch(Dispatchers.IO) {
        ascentRepository.insertAscent(ascent)
    }

    fun getAscent(ascent_id: String): LiveData<Ascent> {
        return ascentRepository.getAscent(ascent_id)
    }

    fun getRoute(routeId: String): LiveData<Route?> {
        return routeRepository.getRoute(routeId)
    }

    fun editAscent(ascent: Ascent) = viewModelScope.launch(Dispatchers.IO) {
        ascentRepository.update(ascent)
    }
}

