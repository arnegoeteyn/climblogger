package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
abstract class AreaDao: BaseDao<Area>() {
    @Query("SELECT * FROM areas ORDER BY name")
    abstract fun getAllAreas(): LiveData<List<Area>>

//    @Insert
//    abstract fun insertArea(area: Area)

    @Query("SELECT * FROM areas WHERE area_uuid == :areaId")
    abstract fun getArea(areaId: String): LiveData<Area>

//    @Delete
//    abstract fun deleteArea(area: Area)
}