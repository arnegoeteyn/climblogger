package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class AreaRepository(private val areaDao: AreaDao) {
    @WorkerThread
    fun insert(area: Area) {
        areaDao.insert(area)
    }

    fun getArea(areaId: String): LiveData<Area> = areaDao.getArea(areaId)

    @WorkerThread
    fun deleteArea(area: Area) {
        areaDao.delete(area)
    }

    @WorkerThread
    fun update(area: Area) {
        return areaDao.update(area)
    }

    val allAreas: LiveData<List<Area>> = areaDao.getAllAreas()
}