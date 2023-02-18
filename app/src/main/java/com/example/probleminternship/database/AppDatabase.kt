package com.example.probleminternship.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.probleminternship.utils.APP_HISTORY

@Database(entities = [AppInfo::class], version = 8, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val appInfoDatabaseDao: AppInfoDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        APP_HISTORY
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}