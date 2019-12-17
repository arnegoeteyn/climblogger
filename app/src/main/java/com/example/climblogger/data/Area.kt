package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "areas")
data class Area(
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "name") val name: String,

    @PrimaryKey
    @ColumnInfo(name = "area_uuid")
    val areaId: String
) {
    override fun toString(): String {
        return "$name - $country"
    }
}

@Dao
abstract class AreaDao : BaseDao<Area>() {
    @Query("SELECT * FROM areas ORDER BY name")
    abstract fun getAllAreas(): LiveData<List<Area>>

    @Query("SELECT * FROM areas WHERE area_uuid == :areaId")
    abstract fun getArea(areaId: String): LiveData<Area>
}


class AreaRepository(private val areaDao: AreaDao) {
    @WorkerThread
    fun insert(area: Area) {
        areaDao.insert(area)
    }

    fun getArea(areaId: String): LiveData<Area> = areaDao.getArea(areaId)

    @WorkerThread
    fun deleteArea(area: Area) {
        areaDao.delete(area)
    }

    @WorkerThread
    fun update(area: Area) {
        return areaDao.update(area)
    }

    val allAreas: LiveData<List<Area>> = areaDao.getAllAreas()
}
