package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AscentDao {

    @Query("SELECT * from ascents ORDER BY date DESC")
    fun getAllAscents(): LiveData<List<Ascent>>

    @Query("SELECT * from ascents where route_id == :route_id ORDER BY date DESC")
    fun ascentsFromRoute(route_id: Int): LiveData<List<Ascent>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAscents(vararg ascents: Ascent)

}