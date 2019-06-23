package com.example.climblogger.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.RouteRoomDatabase
import com.example.climblogger.data.Sector
import com.example.climblogger.data.SectorDao
import com.example.climblogger.data.SectorRepository

class SectorsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SectorRepository

    val allSectors: LiveData<List<Sector>>

    init {
        val sectorDao: SectorDao = RouteRoomDatabase.getDatabase(application).sectorDao()
        repository = SectorRepository(sectorDao)

        allSectors = repository.allSectors
    }
}