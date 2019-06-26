package com.example.climblogger.ui.area

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AreaViewModelFactory(private val application: Application, private val areaId: String) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AreaViewModel(application, areaId) as T
    }
}
