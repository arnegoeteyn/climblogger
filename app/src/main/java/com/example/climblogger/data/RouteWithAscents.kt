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
                select name, grade, routes.kind, route_uuid, count(ascent_uuid) as amount from routes left join ascents USING(route_uuid)
                group by name
                ORDER BY grade desc, name asc
            """
    )
    fun getRoutesWithAscents(): LiveData<List<RouteWithAscents>>

    @Query(
        """
                select name, grade, routes.kind, route_uuid, count(ascent_uuid) as amount from routes left join ascents USING(route_uuid)
                where sector_uuid == :sector_id
                group by name
                ORDER BY grade desc, name asc
            """
    )
    fun getRoutesWithAscents(sector_id: String): LiveData<List<RouteWithAscents>>

    @Query(
        """
                select name, grade, routes.kind, route_uuid, count(ascent_uuid) as amount from routes left join ascents USING(route_uuid)
                group by name
                HAVING amount > 0
                ORDER BY grade desc, name asc
            """
    )
    fun getRoutesWithAscentsWithoutUnAscented(): LiveData<List<RouteWithAscents>>

    @Query(
        """
                select name, grade, routes.kind, route_uuid, count(ascent_uuid) as amount from routes left join ascents USING(route_uuid)
                where sector_uuid == :sector_id 
                group by name
                HAVING amount > 0
                ORDER BY grade desc, name asc
            """
    )
    fun getRoutesWithAscentsWithoutUnAscented(sector_id: String): LiveData<List<RouteWithAscents>>
}


class RouteWithAscentsRepository(private val routeWithAscentsDoa: RouteWithAscentsDoa) {

    /**
     * showNotAscented: Wether to include the routes that are not yet ascented
     */
    fun routesWithAscents(sector_id: String?=null, showNotAscented: Boolean = false): LiveData<List<RouteWithAscents>> {
        sector_id?.let { id ->
            return if(showNotAscented)
                routeWithAscentsDoa.getRoutesWithAscents(id)
            else
                routeWithAscentsDoa.getRoutesWithAscentsWithoutUnAscented(id)
        } ?: run {
            return if(showNotAscented)
                routeWithAscentsDoa.getRoutesWithAscents()
            else
                routeWithAscentsDoa.getRoutesWithAscentsWithoutUnAscented()
        }
    }

}

