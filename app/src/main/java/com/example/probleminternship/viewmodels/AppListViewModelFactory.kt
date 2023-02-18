package com.example.probleminternship.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.probleminternship.database.AppDatabase

class AppListViewModelFactory(private val application: Application, private val db: AppDatabase) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppListViewModel::class.java)) {
            return AppListViewModel(application, db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}