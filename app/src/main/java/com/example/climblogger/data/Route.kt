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
    @ColumnInfo(name = "multipitch_id") val multipitch_id: Int?,
    @PrimaryKey
    @ColumnInfo(name = "route_uuid")
    val route_id: String
) {

    override fun toString(): String {
        return "$name - $grade"
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

}
