package com.example.probleminternship.repository

import com.example.probleminternship.database.TempDatabase
import com.example.probleminternship.database.TemperatureInfo
import com.example.probleminternship.utils.TEMPERATURE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TemperatureRepository(private val database: TempDatabase) {
    fun getTemperature(): List<TemperatureInfo> {
        return database.tempDao.getAll()
    }

    // O adaug in baza de date
    fun refreshTemp(temp: String) {
        val newTemp = TemperatureInfo(1, TEMPERATURE, temp)
        database.tempDao.insert(newTemp)
    }
}