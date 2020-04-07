package com.example.climblogger.ui.route

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ModifyRouteViewModelFactory(
    private val application: Application,
    private val sectorId: String?,
    private val routeId: String?
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ModifyRouteViewModel(application, routeId, sectorId) as T
    }
}
