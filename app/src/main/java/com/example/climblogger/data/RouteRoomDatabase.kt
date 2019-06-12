package com.example.climblogger.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Route::class], version = 1)
public abstract class RouteRoomDatabase : RoomDatabase() {
    abstract fun routeDao(): RouteDao


    companion object {

        @Volatile
        private var INSTANCE: RouteRoomDatabase? = null

        fun getDatabase(context: Context): RouteRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RouteRoomDatabase::class.java,
                    "climb.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}