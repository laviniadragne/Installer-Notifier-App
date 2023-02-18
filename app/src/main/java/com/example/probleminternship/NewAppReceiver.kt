package com.example.probleminternship

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.probleminternship.database.AppDatabase
import com.example.probleminternship.repository.AppRepository
import com.example.probleminternship.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewAppReceiver : BroadcastReceiver() {
    private lateinit var db: AppDatabase
    private lateinit var repository: AppRepository

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent) {
        if (context != null) {
            db = AppDatabase.getDatabase(context)
            repository = AppRepository(db)

            val packageName = intent.data?.encodedSchemeSpecificPart
            val nameApp = repository.getAppName(context, packageName.toString())

            if (intent.action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
                // Delete name and icon
                CoroutineScope(Dispatchers.IO).launch {
                    if (packageName != null) {
                        repository.deleteApp(packageName)
                    }
                }
            } else if (intent.action.equals(Intent.ACTION_PACKAGE_ADDED)) {
                // Add name and icon
                CoroutineScope(Dispatchers.IO).launch {
                    if (packageName != null) {
                        repository.addApp(nameApp, packageName)
                    }
                }
            }

            when (intent.action) {
                Intent.ACTION_PACKAGE_REMOVED -> Toast.makeText(
                    context,
                    context.getString(
                        R.string.app_removed,
                        nameApp
                    ),
                    Toast.LENGTH_LONG
                ).show()

                Intent.ACTION_PACKAGE_ADDED -> Toast.makeText(
                    context,
                    context.getString(
                        R.string.app_added,
                        nameApp
                    ),
                    Toast.LENGTH_LONG
                ).show()
            }

            if (intent.action.equals(Intent.ACTION_PACKAGE_ADDED)) {
                showNotification(
                    context,
                    context.getString(R.string.title_notif_app_added),
                    nameApp
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showNotification(context: Context, title: String, nameApp: String) {

        // Create notification channel
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID_1,
                CH_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = R.string.notif_description.toString()
            mNotificationManager.createNotificationChannel(channel)
        }

        // Create an explicit intent for an Activity
        val intent = Intent(context, MainActivity::class.java)

        intent.putExtra(MSG_TEXT_KEY, nameApp)

        intent.action = MESSAGE_ACTION

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;

        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val message = context.getString(
            R.string.app_added_notification,
            nameApp)

        // Set the notification content
        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID_1)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setOngoing(true)

        // Show notification
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build())
    }
}