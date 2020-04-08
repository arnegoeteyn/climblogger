package com.example.climblogger.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration


@Database(
    entities = [Route::class, Ascent::class, Sector::class, Area::class, Multipitch::class],
    version = 4
)
abstract class RouteRoomDatabase : RoomDatabase() {
    abstract fun routeDao(): RouteDao
    abstract fun ascentDao(): AscentDao
    abstract fun sectorDao(): SectorDao
    abstract fun sectorWithAreaDao(): SectorWithAreaDao
    abstract fun areaDao(): AreaDao
    abstract fun ascentWithRouteDao(): AscentWithRouteDao
    abstract fun routeAmountDao(): RouteAmountDoa
    abstract fun routeWithAscentsDao(): RouteWithAscentsDoa
    abstract fun routeWithSectorDao(): RouteWithSectorDao
    abstract fun multipitchDao(): MultipitchDao

    companion object {

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Since we didn't alter the table, there's nothing else to do here.
            }
        }

        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS multipitches ( multipitch_id integer PRIMARY KEY, name text NOT NULL )"
                )
            }
        }

        val MIGRATION_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        @Volatile
        private var INSTANCE: RouteRoomDatabase? = null

        fun getDatabase(context: Context): RouteRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RouteRoomDatabase::class.java,
                    "climb.db"
                ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}