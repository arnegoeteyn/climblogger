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
import java.util.*

class ModifyAreaViewModel(application: Application, var areaId: String?) :
    AndroidViewModel(application) {
    private val areaRepository: AreaRepository

    var areaCountry: String = "AreaCountry"
    var areaName: String = "AreaName"

    private var loaded = false

    init {
        val areaDao = RouteRoomDatabase.getDatabase(application).areaDao()
        areaRepository = AreaRepository(areaDao)
    }

    fun insertArea() = viewModelScope.launch(Dispatchers.IO) {
        areaRepository.insert(createArea())
    }

    fun editArea() = viewModelScope.launch(Dispatchers.IO) {
        areaRepository.update(createArea())
    }

    private fun createArea(): Area {
        val createdAreaId = areaId ?: UUID.randomUUID().toString()
        return Area(
            areaCountry, areaName, createdAreaId
        )
    }

    fun getArea(): LiveData<Area?>? {
        return areaId?.let { areaRepository.getArea(it) }
    }

    fun updateFromArea(area: Area) {
        if (!loaded) {
            areaName = area.name
            areaCountry = area.country

            loaded = true
        }
    }


}