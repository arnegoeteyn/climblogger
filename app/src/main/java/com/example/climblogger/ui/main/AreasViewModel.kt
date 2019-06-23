package com.example.climblogger.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.Area
import com.example.climblogger.data.AreaRepository
import com.example.climblogger.data.RouteRoomDatabase

class AreasViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AreaRepository

    val allAreas: LiveData<List<Area>>

    init {
        val areaDao = RouteRoomDatabase.getDatabase(application).areaDao()
        repository = AreaRepository(areaDao)

        allAreas = repository.allAreas
    }
}