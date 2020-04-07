package com.example.climblogger.ui.route

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ModifyRouteViewModel(application: Application, var routeId: String?, var sectorId: String?) :
    AndroidViewModel(application) {
    private val routeRepository: RouteRepository
    private val sectorRepository: SectorRepository

    val allSectors: LiveData<List<Sector>>

    var routeName: String = "RouteName"
    var routeGrade: String = "8a"
    var routeLink: String? = null
    var routeComment: String? = null
    var routeKind: String = "sport"

    var loaded = false

    init {
        val routeDao = RouteRoomDatabase.getDatabase(application).routeDao()
        val sectorDao = RouteRoomDatabase.getDatabase(application).sectorDao()
        routeRepository = RouteRepository(routeDao)
        sectorRepository = SectorRepository(sectorDao)

        allSectors = sectorRepository.allSectors

        Log.d("DEBUG", "routeId now is $routeId")
    }

    fun insertRoute() = viewModelScope.launch(Dispatchers.IO) {
        routeRepository.insertRoute(createRoute())
    }

    fun updateRoute() = viewModelScope.launch(Dispatchers.IO) {
        routeRepository.updateRoute(createRoute())
    }

    private fun createRoute(): Route {
        val createdRouteId = routeId ?: UUID.randomUUID().toString()

        return Route(
            sectorId!!,
            routeName,
            routeGrade,
            routeKind,
            routeComment,
            routeLink,
            null,
            null,
            createdRouteId
        )
    }

    fun getSector(sector_id: String): LiveData<Sector> {
        return sectorRepository.getSector(sector_id)
    }

    fun getRoute(): LiveData<Route?>? {
        Log.d("DEBUG", "requested route with $routeId")
        return routeId?.let { routeRepository.getRoute(it) }
    }

    fun updateFromRoute(route: Route) {
        if (!loaded) {
            routeName = route.name
            routeKind = route.kind
            routeComment = route.comment
            routeLink = route.link
            sectorId = route.sector_id

            loaded = true
        }
    }

}