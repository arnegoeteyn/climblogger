package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface AscentWithRouteDao {
    @Query(
        "SELECT routes.*, ascents.route_id as ascent_route_id, ascents.date as ascent_date, ascents.id as ascent_id " +
                "FROM ascents INNER JOIN routes USING (route_id)" +
                "ORDER BY ascent_date DESC"
    )
    fun getAllAscentsWithRoute(): LiveData<List<AscentWithRoute>>
}