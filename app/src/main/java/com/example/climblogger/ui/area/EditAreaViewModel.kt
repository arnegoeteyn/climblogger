package com.example.climblogger.ui.area

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.Area
import com.example.climblogger.data.AreaRepository
import com.example.climblogger.data.RouteRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditAreaViewModel(application: Application) : AndroidViewModel(application) {
    private val areaRepository: AreaRepository

    init {
        val areaDao = RouteRoomDatabase.getDatabase(application).areaDao()
        areaRepository = AreaRepository(areaDao)
    }


    fun insertArea(area: Area) = viewModelScope.launch(Dispatchers.IO) {
        areaRepository.insert(area)
    }


    fun updateArea(area: Area) = viewModelScope.launch(Dispatchers.IO) {
        areaRepository.update(area)
    }


    fun editArea(area: Area) = viewModelScope.launch(Dispatchers.IO) {
        areaRepository.update(area)
    }
}