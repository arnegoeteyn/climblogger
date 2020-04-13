package com.example.climblogger.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.AscentRepository
import com.example.climblogger.data.AscentWithRoute
import com.example.climblogger.data.RouteRoomDatabase

class AscentsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AscentRepository

    val allAscents: LiveData<List<AscentWithRoute>?>

    init {
        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        repository = AscentRepository(ascentDao)

        allAscents = repository.allAscentsWithRoute
    }
}