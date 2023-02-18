package com.example.probleminternship.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TemperatureInfo(
    @PrimaryKey val id: Int,
    @NonNull @ColumnInfo(name = "temperature") val temperature: String,
    @NonNull @ColumnInfo(name = "last_value") val lastValue: String
)