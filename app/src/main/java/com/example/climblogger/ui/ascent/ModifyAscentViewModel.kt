package com.example.climblogger.ui.ascent

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.climblogger.data.*
import com.example.climblogger.util.getStringDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ModifyAscentViewModel(
    application: Application,
    var ascentRouteId: String?,
    var ascentId: String?
) :
    AndroidViewModel(application) {

    private val ascentRepository: AscentRepository
    private val routeRepository: RouteRepository

    val allRoutes: LiveData<List<Route>>

    var ascentDate: String = getStringDate()
    var ascentComment: String? = null
    var ascentKind: String = "redpoint"

    var loaded = false

    init {

        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        val routeDao = RouteRoomDatabase.getDatabase(application).routeDao()
        val ascentWithRouteDao = RouteRoomDatabase.getDatabase(application).ascentWithRouteDao()
        ascentRepository = AscentRepository(ascentDao, ascentWithRouteDao)
        routeRepository = RouteRepository(routeDao)

        allRoutes = routeRepository.allRoutes
    }

    fun insertAscent() = viewModelScope.launch(Dispatchers.IO) {
        val l = ascentRepository.insertAscent(createAscent())
        Log.d("DEBUG", "insert was $l")
    }

    fun updateAscent() = viewModelScope.launch(Dispatchers.IO) {
        val l = ascentRepository.update(createAscent())
        Log.d("DEBUG", "insert was $l")
    }

    fun createAscent(): Ascent {
        // TODO there should always be a route ID but maybe it's took from the ascent that's only loaded after the init
        val createdAscentId = ascentId ?: UUID.randomUUID().toString()

        return Ascent(
            ascentRouteId!!, ascentDate, ascentKind, ascentComment, createdAscentId
        )
    }

    fun getAscent(): LiveData<Ascent?>? {
        return ascentId?.let { ascentRepository.getAscent(it) }
    }

    fun getRoute(routeId: String): LiveData<Route?> {
        return routeRepository.getRoute(routeId)
    }

    fun updateFromAscent(ascent: Ascent) {
        if (!loaded) {
            ascentDate = ascent.date
            ascentKind = ascent.kind
            ascentComment = ascent.comment
            ascentRouteId = ascent.route_id
            loaded = true
        }
    }
}

