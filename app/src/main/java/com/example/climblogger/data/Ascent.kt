package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "ascents",
    foreignKeys = [ForeignKey(
        entity = Route::class,
        parentColumns = arrayOf("route_uuid"),
        childColumns = arrayOf("route_uuid"),
        onDelete = CASCADE
    )]
)
data class Ascent(
    @ColumnInfo(name = "route_uuid") val route_id: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "ascent_kind") val kind: String,
    @ColumnInfo(name = "comment") val comment: String?,
    @PrimaryKey
    @ColumnInfo(name = "ascent_uuid")
    val ascent_id: String
) {
    override fun toString(): String {
        return "$route_id - $date"
    }
}


data class AscentWithRoute(
    @Embedded
    val ascent: Ascent?,

    @Relation(
        parentColumn = "route_uuid",
        entityColumn = "route_uuid"
    )
    val route: Route?
)


@Dao
abstract class AscentDao : BaseDao<Ascent>() {

    @Query("SELECT * from ascents ORDER BY date DESC")
    abstract fun getAllAscents(): LiveData<List<Ascent>>

    @Query("SELECT * from ascents where route_uuid == :route_id ORDER BY date DESC")
    abstract fun ascentsFromRoute(route_id: String): LiveData<List<Ascent>>

    @Query("SELECT * FROM ascents WHERE ascent_uuid == :ascent_id")
    abstract fun getAscent(ascent_id: String): LiveData<Ascent?>

    @Query(
        "SELECT * from ascents where ascent_uuid == :ascent_id"
    )
    abstract fun getAscentWithRoute(ascent_id: String): LiveData<AscentWithRoute?>

    @Query(
        "SELECT * from ascents"
    )
    abstract fun getAllAscentsWithRoute(): LiveData<List<AscentWithRoute>?>
}


class AscentRepository(
    private val ascentDao: AscentDao
) {
    val allAscentsWithRoute: LiveData<List<AscentWithRoute>?> = ascentDao.getAllAscentsWithRoute()

    fun loadAscentsFromRoute(route_id: String): LiveData<List<Ascent>> {
        return ascentDao.ascentsFromRoute(route_id)
    }

    fun getAscent(ascent_id: String): LiveData<Ascent?> {
        return ascentDao.getAscent(ascent_id)
    }

    @WorkerThread
    fun update(ascent: Ascent) {
        return ascentDao.update(ascent)
    }

    @WorkerThread
    fun insertAscent(ascent: Ascent): Long {
        return ascentDao.insert(ascent)
    }

    @WorkerThread
    fun deleteAscent(ascent: Ascent) {
        return ascentDao.delete(ascent)
    }

    fun getAscentsWithRoute(ascent_id: String): LiveData<AscentWithRoute?> {
        return ascentDao.getAscentWithRoute(ascent_id)
    }
}
