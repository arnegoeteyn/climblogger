package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class AscentRepository(private val ascentDao: AscentDao) {

    val allAscents: LiveData<List<Ascent>> = ascentDao.getAllAscents()

    fun loadAscentsFromRoute(route_id: Int): LiveData<List<Ascent>> {
        return ascentDao.ascentsFromRoute(route_id)
    }

    @WorkerThread
    suspend fun insertAscent(ascent: Ascent) {
        return ascentDao.insertAscents(ascent)
    }
}