package com.example.climblogger.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.AscentRepository
import com.example.climblogger.data.RouteRoomDatabase

class AscentsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AscentRepository

    val allAscents: LiveData<List<Ascent>>

    init {
        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        repository = AscentRepository(ascentDao)

        allAscents = ascentDao.getAllAscents()
    }
}