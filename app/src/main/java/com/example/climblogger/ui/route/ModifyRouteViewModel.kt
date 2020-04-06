package com.example.climblogger.ui.route

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModifyRouteViewModel(application: Application) : AndroidViewModel(application) {
    private val routeRepository: RouteRepository
    private val sectorRepository: SectorRepository

    val allSectors: LiveData<List<Sector>>

    var route: LiveData<Route?>? = null

    var routeName: String? = null

    init {
        val routeDao = RouteRoomDatabase.getDatabase(application).routeDao()
        val sectorDao = RouteRoomDatabase.getDatabase(application).sectorDao()
        routeRepository = RouteRepository(routeDao)
        sectorRepository = SectorRepository(sectorDao)

        allSectors = sectorRepository.allSectors
    }

    fun insertRoute(route: Route) = viewModelScope.launch(Dispatchers.IO) {
        routeRepository.insertRoute(route)
    }

    fun getSector(sector_id: String): LiveData<Sector> {
        return sectorRepository.getSector(sector_id)
    }

    fun getRoute(route_id: String): LiveData<Route?>? {
//        route?: run {
//            this.route = routeRepository.getRoute(route_id)
//        }
//        return this.route
        return routeRepository.getRoute(route_id)
    }

    fun editRoute(route: Route) = viewModelScope.launch(Dispatchers.IO) {
        routeRepository.updateRoute(route)
    }
}