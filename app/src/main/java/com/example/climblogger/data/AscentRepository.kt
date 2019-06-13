package com.example.climblogger.data

import androidx.lifecycle.LiveData

class AscentRepository(private val ascentDao: AscentDao) {
    fun ascentsFromRoute(route_id: Int): LiveData<List<Ascent>> = ascentDao.ascentsFromRoute(route_id)
}