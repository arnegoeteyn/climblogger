package com.example.climblogger.ui.ascent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditAscentViewModel(application: Application) : AndroidViewModel(application) {
    private val ascentRepository: AscentRepository

    init {
        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        val ascentWithRouteDao = RouteRoomDatabase.getDatabase(application).ascentWithRouteDao()
        ascentRepository = AscentRepository(ascentDao, ascentWithRouteDao)
    }


    fun insertAscent(ascent: Ascent) = viewModelScope.launch(Dispatchers.IO) {
        ascentRepository.insertAscent(ascent)
    }


    fun updateAscent(ascent: Ascent) = viewModelScope.launch(Dispatchers.IO) {
        ascentRepository.update(ascent)
    }


    fun editAscent(ascent: Ascent) = viewModelScope.launch(Dispatchers.IO) {
        ascentRepository.update(ascent)
    }
}