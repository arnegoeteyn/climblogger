package com.example.climblogger.ui.ascent

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.climblogger.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModifyAscentViewModel(application: Application) :
    AndroidViewModel(application) {

    private val ascentRepository: AscentRepository
    private val routeRepository: RouteRepository

    val allRoutes: LiveData<List<Route>>

    private var draft: MutableLiveData<Ascent.AscentDraft> = MutableLiveData(Ascent.AscentDraft())

    var loadAscentId: String? = null

    init {

        val ascentDao = RouteRoomDatabase.getDatabase(application).ascentDao()
        val routeDao = RouteRoomDatabase.getDatabase(application).routeDao()
        val ascentWithRouteDao = RouteRoomDatabase.getDatabase(application).ascentWithRouteDao()
        ascentRepository = AscentRepository(ascentDao, ascentWithRouteDao)
        routeRepository = RouteRepository(routeDao)

        allRoutes = routeRepository.allRoutes
    }

    /*
        Communication with the repositories
     */

    /**
     * Insert an ascent in the DB.
     * This can be used to add a new ascent or update one that's already in the DB
     */
    fun insertAscent() = viewModelScope.launch(Dispatchers.IO) {
        draft.value?.let {
            Log.d("Ascent", "Inserted ${it.comment}")
            ascentRepository.insertAscent(it)
        }
    }


    /**
     * Get the current ascent.
     * If a loadAscentId has been specified somewhere else that ascent will be returned
     * and the [loadAscentId] will be put to null.
     */
    fun getAscent(): LiveData<Ascent.AscentDraft?> {
        loadAscentId?.let {
            val ld = loadAscent(it)
            loadAscentId = null
            return ld
        }
        return draft
    }

    fun getRoute(routeId: String): LiveData<Route?> {
        return routeRepository.getRoute(routeId)
    }

    /**
     * Load data from an ascent into the draft
     */
    private fun loadAscent(ascent_id: String): LiveData<Ascent.AscentDraft?> {
        return Transformations.switchMap(
            ascentRepository.getAscent(ascent_id),
            this::initDraftAscent
        )
    }


    /*
        Functions for mutating our draft Ascent
     */

    /**
     * Init a draft. If an ascent is passed we use a draft version of that.
     * Otherwise we create a new ascent
     */
    private fun initDraftAscent(ascent: Ascent?): LiveData<Ascent.AscentDraft?> {
        ascent?.let {
            draft.value = it.toDraft()
        } ?: run {
            draft.value = Ascent.AscentDraft()
        }
        return draft
    }

    fun setComment(comment: String?) {
        if (draft.value?.comment != comment)
            draft.value = draft.value?.copy(comment = comment)
    }

    fun setRouteUUID(uuid: String?) {
        if (draft.value?.route_id != uuid) {
            draft.value = draft.value?.copy(route_id = uuid)
        }
    }

    fun setKind(kind: String?) {
        if (draft.value?.kind != kind)
            draft.value = draft.value?.copy(kind = kind)
    }

    fun setDate(date: String?) {
        if (draft.value?.date != date)
            draft.value = draft.value?.copy(date = date)
    }

    fun setAscentUUID(uuid: String?) {
        if (draft.value?.ascent_id != uuid)
            draft.value = draft.value?.copy(ascent_id = uuid)
    }


}

