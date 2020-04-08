package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(
    tableName = "multipitches"
)
data class Multipitch(
    @PrimaryKey
    @ColumnInfo(name = "multipitch_id") val multipitch_id: Int?,
    @ColumnInfo(name = "name") var name: String
    ) {

    override fun toString(): String {
        return "$name - $multipitch_id"
    }
}

@Dao
abstract class MultipitchDao : BaseDao<Multipitch>() {
    @Query("SELECT * from multipitches ORDER BY name ASC")
    abstract fun getAllMultipitches(): LiveData<List<Multipitch>>

    @Query("SELECT * FROM multipitches WHERE multipitch_id == :multipitch_id")
    abstract fun getMultipitch(multipitch_id: String): LiveData<Multipitch?>
}


class MultipitchRepository(private val multipitchDao: MultipitchDao) {

    fun getMultipitch(multipitchId: String): LiveData<Multipitch?> {
        return multipitchDao.getMultipitch(multipitchId)
    }

    val allMultipitches: LiveData<List<Multipitch>> = multipitchDao.getAllMultipitches()

}
