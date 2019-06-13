package com.example.climblogger.ui.route

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.AscentRepository
import com.example.climblogger.data.RouteRoomDatabase

class RouteViewModel(application: Application) : AndroidViewModel(application) {
    private val ascentRepository: AscentRepository

    init {
        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        ascentRepository = AscentRepository(ascentDao)
    }

    public fun loadAscentsFromRoute(route_id: Int): LiveData<List<Ascent>> {
        return ascentRepository.loadAscentsFromRoute(route_id)
    }
}