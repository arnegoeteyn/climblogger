package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class SectorRepository(private val sectorDao: SectorDao) {

    @WorkerThread
    fun insert(sector: Sector) {
        sectorDao.insertSector(sector)
    }

    fun getSector(sector_id: String): LiveData<Sector> {
        return sectorDao.getSector(sector_id)
    }

    @WorkerThread
    fun deleteSector(sector: Sector) {
        sectorDao.deleteSector(sector)
    }

    val allSectors: LiveData<List<Sector>> = sectorDao.getAllSectors()
}
