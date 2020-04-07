package com.example.climblogger.ui.sector

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ModifySectorViewModel(application: Application, var sectorId: String?, var areaId: String?) :
    AndroidViewModel(application) {
    private val sectorRepository: SectorRepository
    private val areaRepository: AreaRepository

    val allAreas: LiveData<List<Area>>

    var sectorName: String = "SectorName"
    var sectorComment: String? = null

    var loaded = false

    init {
        val sectorDao = RouteRoomDatabase.getDatabase(application).sectorDao()
        val areaDao = RouteRoomDatabase.getDatabase(application).areaDao()
        sectorRepository = SectorRepository(sectorDao)
        areaRepository = AreaRepository(areaDao)

        allAreas = areaDao.getAllAreas()
    }

    fun insertSector() = viewModelScope.launch(Dispatchers.IO) {
        sectorRepository.insert(createSector())
    }

    fun editSector() = viewModelScope.launch(Dispatchers.IO) {
        sectorRepository.updateSector(createSector())
    }

    private fun createSector(): Sector {
        val createdSectorId = sectorId ?: UUID.randomUUID().toString()

        return Sector(
            sectorName, areaId!!, sectorComment, createdSectorId
        )
    }

    fun getSector(): LiveData<Sector?>? {
        return sectorId?.let { sectorRepository.getSector(it) }
    }

    fun getArea(areaId: String): LiveData<Area?> {
        return areaRepository.getArea(areaId)
    }

    fun updateFromSector(sector: Sector) {
        if (!loaded) {
            sectorName = sector.name
            sectorComment = sector.comment
            areaId = sector.areaId

            loaded = true
        }
    }
}