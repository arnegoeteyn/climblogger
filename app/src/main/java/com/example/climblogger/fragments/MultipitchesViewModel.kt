package com.example.climblogger.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.*

class MultipitchesViewModel(application: Application) :
    AndroidViewModel(application) {

    private val multipitchRepository: MultipitchRepository

    val allMultipitches: LiveData<List<Multipitch>>

    init {
        val multipitchDao = RouteRoomDatabase.getDatabase(application).multipitchDao()
        multipitchRepository = MultipitchRepository(multipitchDao)

        allMultipitches = multipitchRepository.allMultipitches
    }
}