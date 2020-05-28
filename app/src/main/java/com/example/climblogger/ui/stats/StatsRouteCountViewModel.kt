package com.example.climblogger.ui.stats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.climblogger.data.RouteAmount
import com.example.climblogger.data.RouteAmountRepository
import com.example.climblogger.data.RouteRoomDatabase

class StatsRouteCountViewModel(application: Application) : AndroidViewModel(application) {
    private val routeAmountRepository: RouteAmountRepository

    val sportAmounts: LiveData<List<RouteAmount>>
    val boulderAmounts: LiveData<List<RouteAmount>>

    val sportMax: LiveData<Float>
    val boulderMax: LiveData<Float>

    val sportTotal: LiveData<Int>
    val boulderTotal: LiveData<Int>

    init {
        val routeAmountDoa = RouteRoomDatabase.getDatabase(application).routeAmountDao()
        routeAmountRepository = RouteAmountRepository(routeAmountDoa)
        sportAmounts = routeAmountRepository.sportAmounts
        boulderAmounts = routeAmountRepository.boulderAmounts

        sportMax = routeAmountRepository.sportMax
        boulderMax = routeAmountRepository.boulderMax

        sportTotal = routeAmountRepository.getTotalRoutes("sport")
        boulderTotal = routeAmountRepository.getTotalRoutes("boulder")
    }
}

