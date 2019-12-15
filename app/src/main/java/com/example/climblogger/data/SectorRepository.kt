package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class SectorRepository(private val sectorDao: SectorDao) {

    @WorkerThread
    fun insert(sector: Sector) {
        sectorDao.insert(sector)
    }

    fun getSector(sector_id: String): LiveData<Sector> {
        return sectorDao.getSector(sector_id)
    }

    @WorkerThread
    fun deleteSector(sector: Sector) {
        sectorDao.delete(sector)
    }

    @WorkerThread
    fun updateSector(sector: Sector) {
        return sectorDao.update(sector)
    }


    fun sectorsFromArea(areaId: String): LiveData<List<Sector>> {
        return sectorDao.sectorsFromArea(areaId)
    }

    val allSectors: LiveData<List<Sector>> = sectorDao.getAllSectors()
}
