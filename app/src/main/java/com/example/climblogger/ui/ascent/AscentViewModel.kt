package com.example.climblogger.ui.ascent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.AscentRepository
import com.example.climblogger.data.AscentWithRoute
import com.example.climblogger.data.RouteRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AscentViewModel(application: Application, ascent_id: String) : AndroidViewModel(application) {

    private val ascentRepository: AscentRepository

    val ascentWithRoute: LiveData<AscentWithRoute?>

    init {
        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        ascentRepository = AscentRepository(ascentDao)
        ascentWithRoute = ascentRepository.getAscentsWithRoute(ascent_id)
    }

    // wrapper function so it gets called on another thread
    fun deleteAscent(ascent: Ascent) = viewModelScope.launch(Dispatchers.IO) {
        ascentRepository.deleteAscent(ascent)
    }
}