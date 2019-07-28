package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface RouteAmountDoa {

    @Query(
        "select * from (\n" +
                "\tselect grade, count(distinct route_uuid) as 'amount' from ascents inner join routes USING(route_uuid)\n" +
                "\twhere routes.kind like 'sport'\n" +
                "\tgroup by grade\n" +
                ")\n" +
                "\n" +
                "UNION ALL \n" +
                "\n" +
                "select * from (\n" +
                "\tselect 'total' as grade, count(distinct route_uuid) as 'amount' from ascents inner join routes using(route_uuid)\n" +
                "\twhere routes.kind like 'sport'\n" +
                ")\n" +
                "\n" +
                "order by grade desc \n"
    )
    fun getRouteAmounts(): LiveData<List<RouteAmount>>
}