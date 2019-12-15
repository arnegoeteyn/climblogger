package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class AscentDao: BaseDao<Ascent>() {

    @Query("SELECT * from ascents ORDER BY date DESC")
    abstract fun getAllAscents(): LiveData<List<Ascent>>

    @Query("SELECT * from ascents where route_uuid == :route_id ORDER BY date DESC")
    abstract fun ascentsFromRoute(route_id: String): LiveData<List<Ascent>>

    @Query("SELECT * FROM ascents WHERE ascent_uuid == :ascent_id")
    abstract fun getAscent(ascent_id: String): LiveData<Ascent>
}