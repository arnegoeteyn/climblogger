package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface AscentDao {

    @Query("SELECT * from ascents ORDER BY date DESC")
    fun getAllAscents(): LiveData<List<Ascent>>

    @Query("SELECT * from ascents where route_id == :route_id")
    fun ascentsFromRoute(route_id: Int): LiveData<List<Ascent>>

}