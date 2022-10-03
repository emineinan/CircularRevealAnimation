package com.example.circularrevealanimationsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.circularrevealanimationsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButton.setOnClickListener {
            binding.revealAnimationView.showRevealWithDescription("HELLO CIRCULAR REVEAL ANIMATION")
        }
    }
}


