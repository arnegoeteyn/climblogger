package com.example.climblogger.ui.sector

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ModifySectorViewModelFactory(
    private val application: Application,
    private val sectorId: String?,
    private val areaId: String?
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ModifySectorViewModel(application, sectorId, areaId) as T
    }
}
