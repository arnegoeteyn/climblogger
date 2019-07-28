package com.example.climblogger.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration


@Database(entities = [Route::class, Ascent::class, Sector::class, Area::class], version = 2)
public abstract class RouteRoomDatabase : RoomDatabase() {
    abstract fun routeDao(): RouteDao
    abstract fun ascentDao(): AscentDao
    abstract fun sectorDao(): SectorDao
    abstract fun areaDao(): AreaDao
    abstract fun ascentWithRouteDao(): AscentWithRouteDao
    abstract fun routeAmountDao(): RouteAmountDoa

    companion object {

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Since we didn't alter the table, there's nothing else to do here.
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
                ).addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}