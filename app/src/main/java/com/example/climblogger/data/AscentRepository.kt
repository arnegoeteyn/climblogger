package com.example.climblogger.data

import androidx.lifecycle.LiveData

class AscentRepository(private val ascentDao: AscentDao) {

    fun loadAscentsFromRoute(route_id: Int): LiveData<List<Ascent>> {
        return ascentDao.ascentsFromRoute(route_id)
    }

}