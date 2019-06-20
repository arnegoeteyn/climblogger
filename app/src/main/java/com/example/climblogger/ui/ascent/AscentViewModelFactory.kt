package com.example.climblogger.ui.ascent

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class AscentViewModelFactory(private val application: Application, private val ascent_id: Int) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AscentViewModel(application, ascent_id) as T
    }
}
