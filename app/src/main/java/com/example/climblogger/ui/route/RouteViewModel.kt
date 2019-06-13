package com.example.climblogger.ui.route

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.AscentRepository
import com.example.climblogger.data.RouteRoomDatabase

class RouteViewModel(application: Application) : AndroidViewModel(application) {
    private val ascentRepository: AscentRepository

    val ascentsFromRoute: LiveData<List<Ascent>>

    init {
        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        ascentRepository = AscentRepository(ascentDao)
        ascentsFromRoute = ascentRepository.ascentsFromRoute(8)
    }
}