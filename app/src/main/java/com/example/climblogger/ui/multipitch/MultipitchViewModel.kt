package com.example.climblogger.ui.multipitch

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MultipitchViewModel(application: Application, multipitchId: String) :
    AndroidViewModel(application) {
    private val multipitchRepository: MultipitchRepository
//    private val routeRepository: RouteWithAscentsRepository

    val multipitch: LiveData<MultipitchWithRoutes?>
//    val multipitchRoutes: LiveData<List<RouteWithAscents>>

    init {
        val multipitchDao = RouteRoomDatabase.getDatabase(application).multipitchDao()
//        val routeDao = RouteRoomDatabase.getDatabase(application).routeWithAscentsDao()
        multipitchRepository = MultipitchRepository(multipitchDao)
//        routeRepository = RouteWithAscentsRepository(routeDao)

        multipitch = multipitchRepository.getMultipitchWithRoute(multipitchId)
//        multipitchRoutes = routeRepository.routesFromMultipitch(multipitchId)
    }
}