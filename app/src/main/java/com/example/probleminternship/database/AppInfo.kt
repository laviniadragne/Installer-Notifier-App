package com.example.probleminternship.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_table")
data class AppInfo(
    @PrimaryKey(autoGenerate = true)
    var appId: Long = 0L,

    @ColumnInfo(name = "app_name")
    var appName: String = "",

    @ColumnInfo(name = "package_name")
    var packageName: String = ""
)