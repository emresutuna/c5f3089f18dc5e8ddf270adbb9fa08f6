package com.example.spaceexample.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.spaceexample.dao.FavoriteDao
import com.example.spaceexample.dao.SpaceDao
import com.example.spaceexample.models.FavoriteShip
import com.example.spaceexample.models.ShipModel

@Database(entities = [ShipModel::class, FavoriteShip::class], version = 2)
abstract class SpaceDb : RoomDatabase() {

    abstract fun spaceDao(): SpaceDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {

        private var INSTANCE: SpaceDb? = null

        val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE userinfo ADD COLUMN phone TEXT DEFAULT ''")
            }
        }

        fun getAppDatabase(context: Context): SpaceDb? {

            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder<SpaceDb>(
                    context.applicationContext, SpaceDb::class.java, "spaceDb"
                )
                   // .addMigrations(migration_1_2)
                    .allowMainThreadQueries()
                    .build()

            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }


}