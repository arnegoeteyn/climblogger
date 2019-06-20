package com.example.climblogger.ui.ascent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.AscentRepository
import com.example.climblogger.data.RouteRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AscentViewModel(application: Application, ascent_id: Int) : AndroidViewModel(application) {

    private val ascentRepository: AscentRepository

    val ascent: LiveData<Ascent>

    init {
        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        val ascentWithRouteDao = RouteRoomDatabase.getDatabase(application).ascentWithRouteDao()
        ascentRepository = AscentRepository(ascentDao, ascentWithRouteDao)
        ascent = ascentRepository.getAscent(ascent_id)
    }

    // wrapper function so it gets called on another thread
    fun deleteAscent(ascent: Ascent) = viewModelScope.launch(Dispatchers.IO) {
        ascentRepository.deleteAscent(ascent)
    }
}