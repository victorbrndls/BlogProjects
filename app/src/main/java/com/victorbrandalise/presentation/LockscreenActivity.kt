package com.victorbrandalise.presentation

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.victorbrandalise.databinding.ActivityLockscreenBinding

class LockscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLockscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showOnLockScreenAndTurnScreenOn()

        binding = ActivityLockscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun showOnLockScreenAndTurnScreenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
    }

}