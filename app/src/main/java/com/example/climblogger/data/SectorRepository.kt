package com.example.climblogger.data

import androidx.lifecycle.LiveData

class SectorRepository(private val sectorDao: SectorDao) {
    val allSectors: LiveData<List<Sector>> = sectorDao.getAllSectors()
}
