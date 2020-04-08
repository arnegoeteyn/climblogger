package com.example.climblogger.ui.main.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

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