package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


class RouteAmount(val grade: String, val amount: Int, val percentage: Float) {

    override fun toString(): String {
        return "$grade -> $amount"
    }
}

@Dao
interface RouteAmountDoa {

    @Query(
        """
            select * from(
                select grade, count(distinct route_uuid) as 'amount', 
                count(distinct route_uuid)*100.0 / (SELECT count(distinct route_uuid) from ascents inner join routes USING(route_uuid) where routes.route_kind like :kind) as percentage
                from ascents inner join routes USING(route_uuid)
                where routes.route_kind like :kind
                group by grade
                order by grade DESC
            )
            """
    )
    fun getRouteAmounts(kind: String): LiveData<List<RouteAmount>>

    @Query(
        """
            
            select * from (
                select count(distinct route_uuid) as 'amount'
                from ascents inner join routes using(route_uuid)
                where routes.route_kind like :kind
            )
        """
    )
    fun getTotalRoutes(kind: String): LiveData<Int>

    @Query(
        """ 
            select max(percentage) from (
                select count(distinct route_uuid)*100.0 / (SELECT count(distinct route_uuid) from ascents inner join routes USING(route_uuid) where routes.route_kind like :kind) as percentage
                from ascents inner join routes USING(route_uuid)
                where routes.route_kind like :kind
                group by grade
            );"""
    )
    fun getMaxRoutePercentage(kind: String): LiveData<Float>
}


class RouteAmountRepository(private val routeAmountDoa: RouteAmountDoa) {

    val sportAmounts: LiveData<List<RouteAmount>> = routeAmountDoa.getRouteAmounts("sport")
    val boulderAmounts: LiveData<List<RouteAmount>> = routeAmountDoa.getRouteAmounts("boulder")

    val sportMax: LiveData<Float> = routeAmountDoa.getMaxRoutePercentage("sport")
    val boulderMax: LiveData<Float> = routeAmountDoa.getMaxRoutePercentage("boulder")

    fun getTotalRoutes(kind: String): LiveData<Int> {
        return routeAmountDoa.getTotalRoutes(kind)
    }
}
