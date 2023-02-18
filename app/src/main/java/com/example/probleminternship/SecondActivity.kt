package com.example.probleminternship

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.probleminternship.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}