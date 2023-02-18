package com.example.probleminternship.viewmodels

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.probleminternship.App
import com.example.probleminternship.database.AppDatabase
import com.example.probleminternship.database.AppInfo
import com.example.probleminternship.repository.AppRepository

class AppListViewModel(private val viewModelApplication: Application, private val db: AppDatabase) : ViewModel() {
    var apps: LiveData<List<AppInfo>> = db.appInfoDatabaseDao.getAll()
    private var repository: AppRepository = AppRepository(db)

    private val _appList = Transformations.map(apps) {
        createAppsList(it)
    }

    val appList: LiveData<MutableList<App>>
        get() = _appList

    private fun createAppsList(appsInfo: List<AppInfo>): MutableList<App>{
        var i = 0
        val listApps = mutableListOf<App>()

        while (i < appsInfo.size) {
            val icon: Drawable? = repository.getAppIcon(viewModelApplication, appsInfo[i].packageName)
            listApps.add(App(appsInfo[i].appId, appsInfo[i].appName, icon))
            i++
        }

        return listApps
    }
}