package com.example.climblogger.ui.sector

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddSectorViewModel(application: Application) : AndroidViewModel(application) {
    private val sectorRepository: SectorRepository
    private val areaRepository: AreaRepository

    val allAreas: LiveData<List<Area>>

    init {
        val sectorDao = RouteRoomDatabase.getDatabase(application).sectorDao()
        val areaDao = RouteRoomDatabase.getDatabase(application).areaDao()
        sectorRepository = SectorRepository(sectorDao)
        areaRepository = AreaRepository(areaDao)

        allAreas = areaDao.getAllAreas()
    }

    fun insertSector(sector: Sector) = viewModelScope.launch(Dispatchers.IO) {
        sectorRepository.insert(sector)
    }

    fun getArea(areaId: String): LiveData<Area> {
        return areaRepository.getArea(areaId)
    }
}