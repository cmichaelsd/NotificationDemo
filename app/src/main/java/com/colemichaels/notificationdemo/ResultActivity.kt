package com.colemichaels.notificationdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.colemichaels.notificationdemo.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}