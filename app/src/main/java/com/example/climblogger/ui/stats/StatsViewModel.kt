package com.example.climblogger.ui.stats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.RouteAmount
import com.example.climblogger.data.RouteAmountDoa
import com.example.climblogger.data.RouteAmountRepository
import com.example.climblogger.data.RouteRoomDatabase

class StatsViewModel(application: Application) : AndroidViewModel(application) {
    private val routeAmountRepository: RouteAmountRepository

    val routeAmounts: LiveData<List<RouteAmount>>

    init {
        val routeAmountDoa = RouteRoomDatabase.getDatabase(application).routeAmountDao()
        routeAmountRepository = RouteAmountRepository(routeAmountDoa)
        routeAmounts = routeAmountRepository.routeAmounts
    }
}