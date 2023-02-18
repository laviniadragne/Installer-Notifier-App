package com.example.probleminternship.repository

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import com.example.probleminternship.database.AppDatabase
import com.example.probleminternship.database.AppInfo

class AppRepository(dataBase: AppDatabase) {
    var db: AppDatabase = dataBase

    fun addApp(nameApp: String, packageName: String) {
        db.appInfoDatabaseDao.insert(
            AppInfo(
                appName = nameApp,
                packageName = packageName
            )
        )
    }

    fun deleteApp(packageName: String) {
        db.appInfoDatabaseDao.deleteByPackageName(packageName)
    }

    fun getApps(): LiveData<List<AppInfo>> {
        return db.appInfoDatabaseDao.getAll()
    }

    fun getAppName(context: Context?, packageName: String): String {
        val packageManager: PackageManager? =
            context?.packageManager
        val applicationInfo: ApplicationInfo? = try {
            packageManager?.getApplicationInfo(packageName, 0)
        } catch (exception: PackageManager.NameNotFoundException) {
            null
        }
        return (if (applicationInfo != null) packageManager?.getApplicationLabel(applicationInfo) else packageName) as String
    }

    fun getAppIcon(context: Context?, packageName: String): Drawable? {
        val packageManager: PackageManager? =
            context?.packageManager
        val applicationIcon: Drawable? = try {
            packageManager?.getApplicationIcon(packageName)
        } catch (exception: PackageManager.NameNotFoundException) {
            null
        }
        return applicationIcon
    }
}