package com.example.climblogger.ui.ascent

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ModifyAscentViewModelFactory(
    private val application: Application,
    private val routeId: String?,
    private val ascentId: String?
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ModifyAscentViewModel(application, routeId, ascentId) as T
    }
}
