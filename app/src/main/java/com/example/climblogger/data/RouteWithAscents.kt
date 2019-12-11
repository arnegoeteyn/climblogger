package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

class RouteWithAscents(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "grade") val grade: String,
    @ColumnInfo(name = "kind") val kind: String,
    @PrimaryKey
    @ColumnInfo(name = "route_uuid")
    val route_id: String,

    val amount: Int
) {

    override fun toString(): String {
        return "$name -> $amount"
    }
}


@Dao
interface RouteWithAscentsDoa {

    @Query(
        """
                select name, grade, routes.kind, route_uuid, count(name) as amount from routes inner join ascents USING(route_uuid)
                group by name
            """
    )
    fun getRoutesWithAscents(): LiveData<List<RouteWithAscents>>
}


class RouteWithAscentsRepository(private val routeWithAscentsDoa: RouteWithAscentsDoa) {
    val routeWithAscents: LiveData<List<RouteWithAscents>> = routeWithAscentsDoa.getRoutesWithAscents()
}

