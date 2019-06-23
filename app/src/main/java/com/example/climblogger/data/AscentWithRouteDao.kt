package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface AscentWithRouteDao {
    @Query(
        "SELECT routes.*, ascents.route_id as ascent_route_id, ascents.date as ascent_date, ascents.id as ascent_id , ascents.comment as ascent_comment, ascents.kind as ascent_kind " +
                "FROM ascents INNER JOIN routes USING (route_id)" +
                "ORDER BY ascent_date DESC"
    )
    fun getAllAscentsWithRoute(): LiveData<List<AscentWithRoute>>

    @Query(
        "SELECT routes.*, ascents.route_id as ascent_route_id, ascents.date as ascent_date, ascents.id as ascent_id, ascents.comment as ascent_comment, ascents.kind as ascent_kind " +
                "FROM ascents INNER JOIN routes USING (route_id) " +
                "WHERE ascents.id == :ascent_id " +
                "ORDER BY ascent_date DESC"
    )
    fun getAscentWithRoute(ascent_id: Int): LiveData<AscentWithRoute>
}