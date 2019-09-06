package com.example.climblogger.ui.area

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AreaViewModel(application: Application, areaId: String) : AndroidViewModel(application) {


    private val areaRepository: AreaRepository
    private val sectorRepository: SectorRepository

    val area: LiveData<Area>
    val areaSectors: LiveData<List<Sector>>

    init {
        val areaDao = RouteRoomDatabase.getDatabase(application).areaDao()
        val sectorDao = RouteRoomDatabase.getDatabase(application).sectorDao()
        areaRepository = AreaRepository(areaDao)
        sectorRepository = SectorRepository(sectorDao)

        area = areaRepository.getArea(areaId)
        areaSectors = sectorRepository.sectorsFromArea(areaId)
    }

    fun deleteArea(area: Area) = viewModelScope.launch(Dispatchers.IO) {
        areaRepository.deleteArea(area)
    }
}