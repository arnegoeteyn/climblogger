package com.example.climblogger.ui.route

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditRouteViewModel(application: Application) : AndroidViewModel(application) {
    private val routeRepository: RouteRepository
    private val sectorRepository: SectorRepository

    val allSectors: LiveData<List<Sector>>

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
}