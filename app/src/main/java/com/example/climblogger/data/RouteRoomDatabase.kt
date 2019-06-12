package com.example.climblogger.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.huma.room_for_asset.RoomAsset

@Database(entities = [Route::class], version = 2)
public abstract class RouteRoomDatabase : RoomDatabase() {
    abstract fun routeDao(): RouteDao

    companion object {
        @Volatile
        private var INSTANCE: RouteRoomDatabase? = null

        fun getDatabase(context: Context): RouteRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = RoomAsset.databaseBuilder(
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