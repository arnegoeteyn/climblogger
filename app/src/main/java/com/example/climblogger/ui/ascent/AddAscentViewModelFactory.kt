package com.example.climblogger.ui.ascent

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddAscentViewModelFactory(private val application: Application, private val route_id: Int) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddAscentViewModel(application, route_id) as T
    }
}
