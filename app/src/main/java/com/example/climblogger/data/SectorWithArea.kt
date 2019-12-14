package com.example.climblogger.data

import androidx.lifecycle.LiveData
import androidx.room.*

data class SectorWithArea(
    @Embedded
    val sector: Sector,
    val area_name: String
)


@Dao
interface SectorWithAreaDao {

    @Query(
        """
                select sectors.*, areas.name as area_name from sectors left join areas USING(area_uuid)
                WHERE sector_uuid == :sector_uuid
                ORDER BY name asc
            """
    )
    abstract fun getSectorWithArea(sector_uuid: String): LiveData<SectorWithArea>
}


class SectorWithAreaRepository(private val sectorWithAreaDao: SectorWithAreaDao) {

    fun getSector(sector_id: String): LiveData<SectorWithArea> {
        return sectorWithAreaDao.getSectorWithArea(sector_id)
    }
}

