package com.example.climblogger.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "sectors",
    foreignKeys = [ForeignKey(
        entity = Area::class,
        parentColumns = arrayOf("area_uuid"),
        childColumns = arrayOf("area_uuid"),
        onDelete = CASCADE
    )]
)
data class Sector(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "area_uuid") val areaId: String,
    @ColumnInfo(name = "comment") val comment: String?,
    @PrimaryKey
    @ColumnInfo(name = "sector_uuid")
    val sectorId: String
) {
    override fun toString(): String {
        return "$name"
    }
}

data class SectorWithArea(
    @Embedded
    val sector: Sector,
    @Relation(
        parentColumn = "area_uuid",
        entityColumn = "area_uuid"
    )
    val area: Area
)

@Dao
abstract class SectorDao : BaseDao<Sector>() {

    @Query("SELECT * FROM sectors ORDER BY name")
    abstract fun getAllSectors(): LiveData<List<Sector>>

    @Query("SELECT * FROM sectors WHERE sector_uuid = :sector_id")
    abstract fun getSector(sector_id: String): LiveData<Sector?>

    @Query(" SELECT * FROM sectors WHERE area_uuid == :area_id ORDER BY name")
    abstract fun sectorsFromArea(area_id: String): LiveData<List<Sector>>


    @Transaction
    @Query("SELECT * FROM sectors WHERE sector_uuid == :sector_uuid")
    abstract fun getSectorWithArea(sector_uuid: String): LiveData<SectorWithArea?>
}

class SectorRepository(private val sectorDao: SectorDao) {

    @WorkerThread
    fun insert(sector: Sector) {
        sectorDao.insert(sector)
    }

    fun getSector(sector_id: String): LiveData<Sector?> {
        return sectorDao.getSector(sector_id)
    }

    fun getSectorWithArea(sectorId: String): LiveData<SectorWithArea?> {
        return sectorDao.getSectorWithArea(sectorId)
    }

    @WorkerThread
    fun deleteSector(sector: Sector) {
        sectorDao.delete(sector)
    }

    @WorkerThread
    fun updateSector(sector: Sector) {
        return sectorDao.update(sector)
    }


    fun sectorsFromArea(areaId: String): LiveData<List<Sector>> {
        return sectorDao.sectorsFromArea(areaId)
    }

    val allSectors: LiveData<List<Sector>> = sectorDao.getAllSectors()
}
