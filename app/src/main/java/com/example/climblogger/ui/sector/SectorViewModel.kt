package com.example.climblogger.ui.sector

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SectorViewModel(application: Application, sector_id: String) : AndroidViewModel(application) {

    private val sectorRepository: SectorRepository
    private val routeRepository: RouteRepository

    val sector: LiveData<Sector>
    val sectorRoutes: LiveData<List<Route>>

    init {
        val sectorDao: SectorDao = RouteRoomDatabase.getDatabase(application).sectorDao()
        val routeDao: RouteDao = RouteRoomDatabase.getDatabase(application).routeDao()
        sectorRepository = SectorRepository(sectorDao)
        routeRepository = RouteRepository(routeDao)

        sector = sectorRepository.getSector(sector_id)
        sectorRoutes = routeRepository.routesFromSector(sector_id)

    }

    fun deleteSector(sector: Sector) = viewModelScope.launch(Dispatchers.IO) {
        sectorRepository.deleteSector(sector)
    }
}