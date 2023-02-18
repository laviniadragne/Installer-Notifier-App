package com.example.probleminternship.database

import android.content.Context
import androidx.room.*
import com.example.probleminternship.utils.TEMPERATURE

@Dao
interface TempDao {
    @Query("SELECT * FROM temperatureinfo")
    fun getAll(): List<TemperatureInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(temp: TemperatureInfo)
}

@Database(entities = [TemperatureInfo::class], version = 1)
abstract class TempDatabase: RoomDatabase() {
    abstract val tempDao: TempDao
}

private lateinit var INSTANCE: TempDatabase

fun getDatabase(context: Context): TempDatabase {
    synchronized(TempDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                TempDatabase::class.java,
                TEMPERATURE).build()
        }
    }
    return INSTANCE
}
