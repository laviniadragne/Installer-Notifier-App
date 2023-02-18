package com.example.probleminternship

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import com.example.probleminternship.utils.ALARM_ON
import com.example.probleminternship.utils.PACKAGE
import com.example.probleminternship.utils.REQUEST_CODE

class ApplicationInstance : Application() {
    private val broadcastReceiver: NewAppReceiver = NewAppReceiver()
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate() {
        super.onCreate()
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        Toast.makeText(this, ALARM_ON, Toast.LENGTH_SHORT).show()

        // Using intent I have class AlarmReceiver class which inherits
        // BroadcastReceiver
        val intent = Intent(this, AlarmReceiver::class.java)

        // We call broadcast using pendingIntent
        pendingIntent =
            PendingIntent.getBroadcast(this, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 10000,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pendingIntent
        )

        val intentFilter = IntentFilter(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)

        intentFilter.addDataScheme(PACKAGE)
        this.registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(broadcastReceiver)
    }
}