package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class AreaRepository(private val areaDao: AreaDao) {
    @WorkerThread
    fun insert(area: Area) {
        areaDao.insertArea(area)
    }

    val allAreas: LiveData<List<Area>> = areaDao.getAllAreas()
}