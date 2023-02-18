package com.example.probleminternship

import android.annotation.SuppressLint
import android.app.Notification
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
import com.example.probleminternship.database.getDatabase
import com.example.probleminternship.network.TemperatureApi
import com.example.probleminternship.repository.TemperatureRepository
import com.example.probleminternship.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AlarmReceiver : BroadcastReceiver() {
    private var temp = INITIAL_TEMP
    private lateinit var temperatureRepository: TemperatureRepository

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        temperatureRepository = TemperatureRepository(getDatabase(context))

        Toast.makeText(context, ALARM_MESSAGE, Toast.LENGTH_LONG).show()

        getTemperatureValue(temperatureRepository)

        showNotification(
            context,
            "Reminder",
            "Temperature is: $temp \u2103"
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification(context: Context, title: String, message: String) {
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID_2,
                CH_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = EXAMPLE_ALARM
            mNotificationManager.createNotificationChannel(channel)
        }

        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID_2)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)

        mBuilder.setDefaults(Notification.DEFAULT_ALL)

        val intent = Intent(context, MainActivity::class.java)

        val pi = PendingIntent.getActivity(context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)

        mBuilder.setContentIntent(pi)
        mNotificationManager.notify(NOTIFICATION_ALARM_ID, mBuilder.build())
    }

    private fun getTemperatureValue(temperatureRepository: TemperatureRepository) {
        runBlocking {
            val job = launch(context = Dispatchers.Default) {
                try {
                    val response = TemperatureApi.retrofitService.getTemperature()
                    val curr: Map<Any, Any> = response[CURRENTLY] as Map<Any, Any>
                    val tempFahrenheit = curr[TEMPERATURE].toString().toDouble()
                    val tempCelsius: Double = (tempFahrenheit - 32) / 1.8000
                    val tempRounded: Double = String.format("%.3f", tempCelsius).toDouble()
                    temp = tempRounded.toString()

                    // Daca s-a reusit request-ul o adaug si in baza de date
                    temperatureRepository.refreshTemp(temp)
                }

                // No internet
                catch (e: Exception) {
                    // O iau din baza de date daca nu e goala
                    if (temperatureRepository.getTemperature().isNotEmpty()) {
                        temp = temperatureRepository.getTemperature()[0].lastValue
                    }
                }
            }
            job.join()
        }
    }
}
