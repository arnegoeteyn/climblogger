package com.example.climblogger.ui.area

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.Area
import com.example.climblogger.data.AreaRepository
import com.example.climblogger.data.RouteRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Viewmodel for everything needed to modify an area. Adding is a special case of modifying
 * since that's just modifying an empty route
 */
class ModifyAreaViewModel(application: Application) : AndroidViewModel(application) {
    private val areaRepository: AreaRepository

    init {
        val areaDao = RouteRoomDatabase.getDatabase(application).areaDao()
        areaRepository = AreaRepository(areaDao)
    }

    fun insertArea(area: Area) = viewModelScope.launch(Dispatchers.IO) {
        areaRepository.insert(area)
    }

    fun getArea(area_id: String): LiveData<Area?> {
        return areaRepository.getArea(area_id)
    }

    fun editArea(area: Area) = viewModelScope.launch(Dispatchers.IO) {
        areaRepository.update(area)
    }

}