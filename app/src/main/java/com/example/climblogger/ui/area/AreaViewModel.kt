package com.example.climblogger.ui.area

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.Area
import com.example.climblogger.data.AreaRepository
import com.example.climblogger.data.RouteRoomDatabase

class AreaViewModel(application: Application, areaId: String) : AndroidViewModel(application) {

    private val areaRepository: AreaRepository

    val area: LiveData<Area>

    init {
        val areaDao = RouteRoomDatabase.getDatabase(application).areaDao()
        areaRepository = AreaRepository(areaDao)

        area = areaRepository.getArea(areaId)
    }
}