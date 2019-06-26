package com.example.climblogger.ui.sector

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.RouteRoomDatabase
import com.example.climblogger.data.Sector
import com.example.climblogger.data.SectorDao
import com.example.climblogger.data.SectorRepository

class SectorViewModel(application: Application, sector_id: String) : AndroidViewModel(application) {
    private val sectorRepository: SectorRepository

    val sector: LiveData<Sector>

    init {
        val sectorDao: SectorDao = RouteRoomDatabase.getDatabase(application).sectorDao()
        sectorRepository = SectorRepository(sectorDao)

        sector = sectorRepository.getSector(sector_id)

    }
}