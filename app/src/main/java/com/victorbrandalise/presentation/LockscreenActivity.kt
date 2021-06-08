package com.victorbrandalise.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.victorbrandalise.databinding.ActivityLockscreenBinding

class LockscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLockscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLockscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}