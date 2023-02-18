package com.example.probleminternship.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AppInfoDao {
    @Insert
    fun insert(appInfo: AppInfo)

    @Query("SELECT * from app_table")
    fun getAll(): LiveData<List<AppInfo>>

    @Query("DELETE FROM app_table WHERE package_name = :appPackage")
    fun deleteByPackageName(appPackage: String)

    @Query("DELETE FROM app_table")
    fun clear()
}