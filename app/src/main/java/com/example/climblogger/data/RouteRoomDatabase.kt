package com.example.climblogger.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration


@Database(
    entities = [Route::class, Ascent::class, Sector::class, Area::class, Multipitch::class],
    version = 5
)
abstract class RouteRoomDatabase : RoomDatabase() {
    abstract fun routeDao(): RouteDao
    abstract fun ascentDao(): AscentDao
    abstract fun sectorDao(): SectorDao
    abstract fun areaDao(): AreaDao
    abstract fun multipitchDao(): MultipitchDao
    abstract fun routeAmountDao(): RouteAmountDoa

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

        val MIGRATION_4_5: Migration = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("ALTER TABLE routes RENAME TO tmp_routes;")
                database.execSQL("ALTER TABLE ascents RENAME TO tmp_ascents;")
                database.execSQL(
                    "CREATE TABLE `routes` (\n" +
                            "`route_kind` TEXT NOT NULL,\n" +
                            "`grade` TEXT NOT NULL,\n" +
                            "`comment` TEXT,\n" +
                            "`name` TEXT NOT NULL,\n" +
                            "`link` TEXT,\n" +
                            "`multipitch_uuid` TEXT,\n" +
                            "`pitch` INTEGER,\n" +
                            "`sector_uuid` TEXT NOT NULL,\n" +
                            "`route_uuid` TEXT NOT NULL UNIQUE,\n" +
                            "FOREIGN KEY(`sector_uuid`) REFERENCES `sectors`(`sector_uuid`) ON DELETE CASCADE,\n" +
                            "PRIMARY KEY(`route_uuid`)\n" +
                            ");"
                )

                database.execSQL(
                    "CREATE TABLE `ascents` (\n" +
                            " `date` TEXT NOT NULL,\n" +
                            " `ascent_kind` TEXT NOT NULL,\n" +
                            " `comment` TEXT DEFAULT NULL,\n" +
                            " `route_uuid` TEXT NOT NULL,\n" +
                            " `ascent_uuid` TEXT NOT NULL,\n" +
                            " FOREIGN KEY(`route_uuid`) REFERENCES `routes`(`route_uuid`) ON DELETE CASCADE,\n" +
                            " PRIMARY KEY(`ascent_uuid`)\n" +
                            ");"
                )

                database.execSQL(
                    "INSERT INTO `ascents` " +
                            "(`date`, `ascent_kind`, `comment`, `route_uuid`,  `ascent_uuid` )\n" +
                            "SELECT `date`, `kind`, `comment`, `route_uuid`,  `ascent_uuid` FROM tmp_ascents;"
                )
                database.execSQL(
                    "INSERT INTO `routes` " +
                            "(`route_kind`, `grade`, `comment`, `name`, `link`, `multipitch_uuid`, `pitch`, `sector_uuid`, `route_uuid`)\n" +
                            "SELECT `kind`, `grade`, `comment`, `name`, `link`, `multipitch_uuid`, `pitch`, `sector_uuid`, `route_uuid` FROM tmp_routes;"
                )

                database.execSQL(
                    "DROP TABLE tmp_routes;"
                )
                database.execSQL("DROP TABLE tmp_ascents;")

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
                ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}