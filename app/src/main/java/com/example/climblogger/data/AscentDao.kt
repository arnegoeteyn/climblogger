package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AscentDao {

    @Query("SELECT * from ascents ORDER BY date DESC")
    fun getAllAscents(): LiveData<List<Ascent>>

    @Query("SELECT * from ascents where route_uuid == :route_id ORDER BY date DESC")
    fun ascentsFromRoute(route_id: String): LiveData<List<Ascent>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAscents(vararg ascents: Ascent)

    @Delete
    fun deleteAscent(ascent: Ascent)

    @Query("SELECT * FROM ascents WHERE ascent_uuid == :ascent_id")
    fun getAscent(ascent_id: String): LiveData<Ascent>
}