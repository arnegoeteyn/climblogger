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
    private val sectorWithAreaRepository: SectorWithAreaRepository

    val sector: LiveData<Sector?>
    val sectorWithArea: LiveData<SectorWithArea?>
    val sectorRoutes: LiveData<List<RouteWithAscents>>

    init {
        val sectorDao: SectorDao = RouteRoomDatabase.getDatabase(application).sectorDao()
        val routeDao: RouteDao =
            RouteRoomDatabase.getDatabase(application).routeDao()
        val sectorWithAreaDao: SectorWithAreaDao =
            RouteRoomDatabase.getDatabase(application).sectorWithAreaDao()

        sectorRepository = SectorRepository(sectorDao)
        routeRepository = RouteRepository(routeDao)
        sectorWithAreaRepository = SectorWithAreaRepository(sectorWithAreaDao)

        sector = sectorRepository.getSector(sector_id)
        sectorWithArea = sectorWithAreaDao.getSectorWithArea(sector_id)
        sectorRoutes = routeRepository.routesWithAscents(sector_id, true)

    }

    fun deleteSector(sector: Sector) = viewModelScope.launch(Dispatchers.IO) {
        sectorRepository.deleteSector(sector)
    }
}