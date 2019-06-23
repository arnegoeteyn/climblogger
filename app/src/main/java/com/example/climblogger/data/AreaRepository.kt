package com.example.climblogger.data

import androidx.lifecycle.LiveData

class AreaRepository(private val areaDao: AreaDao) {
    val allAreas: LiveData<List<Area>> = areaDao.getAllAreas()
}