package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class SectorRepository(private val sectorDao: SectorDao) {

    @WorkerThread
    fun insert(sector: Sector) {
        sectorDao.insertSector(sector)
    }

    val allSectors: LiveData<List<Sector>> = sectorDao.getAllSectors()
}
