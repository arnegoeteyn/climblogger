package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


class RouteAmount(val grade: String, val amount: Int) {

    override fun toString(): String {
        return "$grade -> $amount"
    }
}

@Dao
interface RouteAmountDoa {

    @Query(
        """
            select * from (
                select grade, count(distinct route_uuid) as 'amount' from ascents inner join routes USING(route_uuid)
                where routes.kind like :kind
                group by grade
            )
            UNION ALL
            select * from (
                select 'total' as grade, count(distinct route_uuid) as 'amount' from ascents inner join routes using(route_uuid)
                where routes.kind like :kind
            )
            order by grade desc
            """
    )
    fun getRouteAmounts(kind: String): LiveData<List<RouteAmount>>
}


class RouteAmountRepository(private val routeAmountDoa: RouteAmountDoa) {

    val sportAmounts: LiveData<List<RouteAmount>> = routeAmountDoa.getRouteAmounts("sport")
    val boulderAmounts: LiveData<List<RouteAmount>> = routeAmountDoa.getRouteAmounts("boulder")
}
