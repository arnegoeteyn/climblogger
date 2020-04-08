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

@Dao
abstract class MultipitchDao : BaseDao<Multipitch>() {
    @Query("SELECT * from multipitches ORDER BY name ASC")
    abstract fun getAllMultipitches(): LiveData<List<Multipitch>>

    @Query("SELECT * FROM multipitches WHERE multipitch_uuid == :multipitch_uuid")
    abstract fun getMultipitch(multipitch_uuid: String): LiveData<Multipitch?>
}


class MultipitchRepository(private val multipitchDao: MultipitchDao) {

    fun getMultipitch(multipitchId: String): LiveData<Multipitch?> {
        return multipitchDao.getMultipitch(multipitchId)
    }

    val allMultipitches: LiveData<List<Multipitch>> = multipitchDao.getAllMultipitches()

}
