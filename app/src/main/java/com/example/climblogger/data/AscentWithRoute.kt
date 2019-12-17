package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class AscentWithRoute(
    @Embedded(prefix = "ascent_")
    val ascent: Ascent,
    @Embedded
    val route: Route
)

@Dao
interface AscentWithRouteDao {
    @Query(
        "SELECT routes.*, ascents.route_uuid as ascent_route_uuid, ascents.date as ascent_date, ascents.ascent_uuid as ascent_ascent_uuid , ascents.comment as ascent_comment, ascents.kind as ascent_kind " +
                "FROM ascents INNER JOIN routes USING (route_uuid)" +
                "ORDER BY ascent_date DESC"
    )
    fun getAllAscentsWithRoute(): LiveData<List<AscentWithRoute>>

    @Query(
        "SELECT routes.*, ascents.route_uuid as ascent_route_uuid, ascents.date as ascent_date, ascents.ascent_uuid as ascent_ascent_uuid, ascents.comment as ascent_comment, ascents.kind as ascent_kind " +
                "FROM ascents INNER JOIN routes USING (route_uuid) " +
                "WHERE ascents.ascent_uuid == :ascent_id " +
                "ORDER BY ascent_date DESC"
    )
    fun getAscentWithRoute(ascent_id: String): LiveData<AscentWithRoute>
}
