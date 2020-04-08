package com.example.climblogger.ui.multipitch

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MultipitchViewModelFactory(
    private val application: Application,
    private val multipitchId: String
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MultipitchViewModel(application, multipitchId) as T
    }
}
