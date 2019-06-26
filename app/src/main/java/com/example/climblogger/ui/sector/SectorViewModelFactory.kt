package com.example.climblogger.ui.sector

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SectorViewModelFactory(private val application: Application, private val sectorId: String) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SectorViewModel(application, sectorId) as T
    }
}