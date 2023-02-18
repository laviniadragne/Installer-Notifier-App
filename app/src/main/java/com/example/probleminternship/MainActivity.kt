package com.example.probleminternship

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.probleminternship.databinding.ActivityMainBinding
import com.example.probleminternship.utils.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        if (intent != null) {
            if (intent.extras?.getString(MSG_TEXT_KEY) != null) {
                val nameApp = intent.extras?.getString(MSG_TEXT_KEY)
                createDialogAlert(nameApp)
            }
        }

        setContentView(binding.root)
    }

    private fun createDialogAlert(nameApp: String?) {
        // Extract icon
        val message = getString(
            R.string.app_dialog,
            nameApp
        )

        // Create AlertDialog
        AlertDialog.Builder(this)
            .setTitle(INSTALLED_APP)
            .setMessage(message)
            .setIcon(R.drawable.ic_stat_name)
            .show()
        intent.removeExtra(MSG_TEXT_KEY)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}