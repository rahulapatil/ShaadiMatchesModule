package com.example.shaadimatchesmodule.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShadiMatchEntity::class], version = 1,exportSchema = false)
abstract class ShadiMatchDatabase : RoomDatabase() {

    abstract fun shadiMatchDao(): ShadiMatchDao

    companion object {
        @Volatile
        private var instance: ShadiMatchDatabase? = null
        fun getInstance(context: Context) : ShadiMatchDatabase {

            if (instance != null) return instance!!

            synchronized(this) {

                instance = Room
                    .databaseBuilder(context, ShadiMatchDatabase::class.java, "shadi_match_database")
                    .fallbackToDestructiveMigration()
                    .build()

                return instance!!

            }
        }


    }
}