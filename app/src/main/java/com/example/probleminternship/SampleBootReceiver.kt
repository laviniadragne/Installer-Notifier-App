package com.example.probleminternship

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.probleminternship.utils.*

class SampleBootReceiver : BroadcastReceiver() {
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    companion object {
        private const val TAG = "AlarmBoot"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        if ("android.intent.action.BOOT_COMPLETED" == intent.action) {
            alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            Log.d(TAG,"Dupa REBOOT");
            Toast.makeText(context, ALARM_ON, Toast.LENGTH_SHORT).show()

            // Using intent I have class AlarmReceiver class which inherits
            // BroadcastReceiver
            val intent = Intent(context, AlarmReceiver::class.java)

            // We call broadcast using pendingIntent
            pendingIntent =
                PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 10000,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                pendingIntent
            )
        }
    }
}