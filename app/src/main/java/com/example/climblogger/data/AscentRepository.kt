package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class AscentRepository(private val ascentDao: AscentDao, private val ascentWithRouteDao: AscentWithRouteDao) {

    val allAscents: LiveData<List<Ascent>> = ascentDao.getAllAscents()
    val allAscentsWithRoute: LiveData<List<AscentWithRoute>> = ascentWithRouteDao.getAllAscentsWithRoute()

    fun loadAscentsFromRoute(route_id: String): LiveData<List<Ascent>> {
        return ascentDao.ascentsFromRoute(route_id)
    }

    fun getAscent(ascent_id: String): LiveData<Ascent> {
        return ascentDao.getAscent(ascent_id)
    }

    @WorkerThread
    suspend fun insertAscent(ascent: Ascent) {
        return ascentDao.insertAscents(ascent)
    }

    @WorkerThread
    suspend fun deleteAscent(ascent: Ascent) {
        return ascentDao.deleteAscent(ascent)
    }

    fun getAscentsWithRoute(ascent_id: String): LiveData<AscentWithRoute> {
        return ascentWithRouteDao.getAscentWithRoute(ascent_id)
    }
}