package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "routes",
    foreignKeys = [ForeignKey(
        entity = Sector::class,
        parentColumns = arrayOf("sector_uuid"),
        childColumns = arrayOf("sector_uuid"),
        onDelete = CASCADE
    )]
)
data class Route(
    @ColumnInfo(name = "sector_uuid") val sector_id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "grade") val grade: String,
    @ColumnInfo(name = "kind") val kind: String,
    @ColumnInfo(name = "comment") val comment: String?,
    @ColumnInfo(name = "link") val link: String?,
    @ColumnInfo(name = "pitch") val pitch: Int?,
    @ColumnInfo(name = "multipitch_uuid") val multipitch_uuid: String?,
    @PrimaryKey
    @ColumnInfo(name = "route_uuid")
    val route_id: String
) {

    override fun toString(): String {
        return "$name - $grade"
    }
}

data class RouteWithAscents(
    @Embedded
    val route: Route,
    @Relation(
        parentColumn = "route_uuid",
        entityColumn = "route_uuid"
    )
    val ascents: List<Ascent>
) {
    override fun toString(): String {
        return "${route.name} with ${ascents.size} ascents"
    }
}

@Dao
abstract class RouteDao : BaseDao<Route>() {

    @Query("SELECT * from routes ORDER BY grade DESC, name ASC")
    abstract fun getAllRoutes(): LiveData<List<Route>>

    @Query("SELECT * FROM routes WHERE route_uuid == :route_id")
    abstract fun getRoute(route_id: String): LiveData<Route?>

    @Query(" SELECT * FROM routes WHERE sector_uuid == :sector_id ORDER BY grade DESC")
    abstract fun routesFromSector(sector_id: String): LiveData<List<Route>>


    @Query(
        """
                SELECT * from routes
                group by name
                ORDER BY grade desc, name asc
            """
    )
    abstract fun getRoutesWithAscents(): LiveData<List<RouteWithAscents>>

    @Query(
        """
                SELECT * from routes
                where sector_uuid == :sector_id
                group by name
                ORDER BY grade desc, name asc
            """
    )
    abstract fun getRoutesWithAscents(sector_id: String): LiveData<List<RouteWithAscents>>

    @Query(
        """
                SELECT *, count(ascents.ascent_uuid) as amount from routes inner join ascents using(route_uuid)
                group by name
                HAVING amount > 0
                ORDER BY grade desc, name asc
            """
    )
    abstract fun getRoutesWithAscentsWithoutUnAscented(): LiveData<List<RouteWithAscents>>

    @Query(
        """
                SELECT *, count(ascents.ascent_uuid) as amount from routes inner join ascents using(route_uuid)
                where sector_uuid == :sector_id 
                group by name
                HAVING amount > 0
                ORDER BY grade desc, name asc
            """
    )
    abstract fun getRoutesWithAscentsWithoutUnAscented(sector_id: String): LiveData<List<RouteWithAscents>>

    @Query(
        """
                SELECT * from routes
                where multipitch_uuid == :multipitchId 
                group by name
                ORDER BY pitch asc
            """
    )
    abstract fun routesFromMultipitch(multipitchId: String): LiveData<List<RouteWithAscents>>
}


class RouteRepository(private val routeDao: RouteDao) {

    fun getRoute(route_id: String): LiveData<Route?> {
        return routeDao.getRoute(route_id)
    }

    @WorkerThread
    fun insertRoute(route: Route): Long {
        return routeDao.insert(route)
    }

    @WorkerThread
    fun updateRoute(route: Route) {
        return routeDao.update(route)
    }

    @WorkerThread
    fun deleteRoute(route: Route) {
        return routeDao.delete(route)
    }

    val allRoutes: LiveData<List<Route>> = routeDao.getAllRoutes()

    fun routesFromSector(sector_id: String): LiveData<List<Route>> {
        return routeDao.routesFromSector(sector_id)
    }

    fun routesWithAscents(
        sector_id: String? = null,
        showNotAscented: Boolean = false
    ): LiveData<List<RouteWithAscents>> {
        sector_id?.let { id ->
            return if (showNotAscented)
                routeDao.getRoutesWithAscents(id)
            else
                routeDao.getRoutesWithAscentsWithoutUnAscented(id)
        } ?: run {
            return if (showNotAscented)
                routeDao.getRoutesWithAscents()
            else
                routeDao.getRoutesWithAscentsWithoutUnAscented()
        }
    }

    fun routesFromMultipitch(multipitchId: String): LiveData<List<RouteWithAscents>> {
        return routeDao.routesFromMultipitch(multipitchId)
    }

}
