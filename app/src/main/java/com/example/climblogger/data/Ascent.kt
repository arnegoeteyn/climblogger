package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.climblogger.util.getStringDate

interface Draftable<Me : Draftable<Me>> {

    fun toDraft(): Draft<Me>

    interface Draft<Me : Draftable<Me>> {
        fun fromDraft(): Draftable<Me>?
    }

}

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
    @ColumnInfo(name = "kind") val kind: String,
    @ColumnInfo(name = "comment") val comment: String?,
    @PrimaryKey
    @ColumnInfo(name = "ascent_uuid")
    val ascent_id: String
) : Draftable<Ascent> {
    override fun toString(): String {
        return "$route_id - $date"
    }

    override fun toDraft(): AscentDraft {
        return AscentDraft(
            route_id, date, kind, comment, ascent_id
        )
    }

    data class AscentDraft(
        val route_id: String? = null,
        val date: String? = getStringDate(),
        val kind: String? = "redpoint",
        val comment: String? = null,
        val ascent_id: String? = null
    ) : Draftable.Draft<Ascent> {
        override fun fromDraft(): Ascent? {
            if (route_id != null && date != null && kind != null && ascent_id != null)
                return Ascent(
                    route_id, date, kind, comment, ascent_id
                )
            return null
        }

    }
}


@Dao
abstract class AscentDao : BaseDao<Ascent>() {

    @Query("SELECT * from ascents ORDER BY date DESC")
    abstract fun getAllAscents(): LiveData<List<Ascent>>

    @Query("SELECT * from ascents where route_uuid == :route_id ORDER BY date DESC")
    abstract fun ascentsFromRoute(route_id: String): LiveData<List<Ascent>>

    @Query("SELECT * FROM ascents WHERE ascent_uuid == :ascent_id")
    abstract fun getAscent(ascent_id: String): LiveData<Ascent?>
}


class AscentRepository(
    private val ascentDao: AscentDao,
    private val ascentWithRouteDao: AscentWithRouteDao
) {

    val allAscents: LiveData<List<Ascent>> = ascentDao.getAllAscents()
    val allAscentsWithRoute: LiveData<List<AscentWithRoute>> =
        ascentWithRouteDao.getAllAscentsWithRoute()

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
    fun insertAscent(ascent: Ascent.AscentDraft) {
        ascent.fromDraft()?.let {
            return ascentDao.upsert(it)
        }
    }

    @WorkerThread
    fun deleteAscent(ascent: Ascent) {
        return ascentDao.delete(ascent)
    }

    fun getAscentsWithRoute(ascent_id: String): LiveData<AscentWithRoute> {
        return ascentWithRouteDao.getAscentWithRoute(ascent_id)
    }
}
