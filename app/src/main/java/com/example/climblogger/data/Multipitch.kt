package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(
    tableName = "multipitches"
)
data class Multipitch(
    @PrimaryKey
    @ColumnInfo(name = "multipitch_uuid") val multipitch_uuid: String,
    @ColumnInfo(name = "name") var name: String
) {

    override fun toString(): String {
        return "$name - $multipitch_uuid"
    }
}


data class MultipitchWithRoutes(
    @Embedded val multipitch: Multipitch,
    @Relation(
        parentColumn = "multipitch_uuid",
        entityColumn = "multipitch_uuid"
    )
    val routes: List<Route>
) {
    override fun toString(): String {
        return "${multipitch.name} with ${routes.size} routes"
    }
}

@Dao
abstract class MultipitchDao : BaseDao<Multipitch>() {
    @Query("SELECT * from multipitches ORDER BY name ASC")
    abstract fun getAllMultipitches(): LiveData<List<Multipitch>>

    @Query("SELECT * FROM multipitches WHERE multipitch_uuid == :multipitch_uuid")
    abstract fun getMultipitch(multipitch_uuid: String): LiveData<Multipitch?>

    @Query("SELECT * FROM multipitches WHERE multipitch_uuid == :multipitch_uuid")
    abstract fun getMultipitchWithRoute(multipitch_uuid: String): LiveData<MultipitchWithRoutes?>
}

class MultipitchRepository(private val multipitchDao: MultipitchDao) {

    fun getMultipitch(multipitchId: String): LiveData<Multipitch?> {
        return multipitchDao.getMultipitch(multipitchId)
    }

    fun getMultipitchWithRoute(multipitchId: String): LiveData<MultipitchWithRoutes?> {
        return multipitchDao.getMultipitchWithRoute(multipitchId)
    }

    val allMultipitches: LiveData<List<Multipitch>> = multipitchDao.getAllMultipitches()

}
