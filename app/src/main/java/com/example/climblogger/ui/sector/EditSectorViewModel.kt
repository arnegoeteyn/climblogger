package com.example.climblogger.ui.sector

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditSectorViewModel(application: Application) : AndroidViewModel(application) {
    private val sectorRepository: SectorRepository
    private val areaRepository: AreaRepository

    val allAreas: LiveData<List<Area>>

    init {
        val sectorDao = RouteRoomDatabase.getDatabase(application).sectorDao()
        val areaDao = RouteRoomDatabase.getDatabase(application).areaDao()
        sectorRepository = SectorRepository(sectorDao)
        areaRepository = AreaRepository(areaDao)

        allAreas = areaRepository.allAreas
    }

    fun insertSector(sector: Sector) = viewModelScope.launch(Dispatchers.IO) {
        sectorRepository.insert(sector)
    }

    fun getArea(area_id: String): LiveData<Area> {
        return areaRepository.getArea(area_id)
    }

    fun editSector(sector: Sector) = viewModelScope.launch(Dispatchers.IO) {
        sectorRepository.updateSector(sector)
    }
}