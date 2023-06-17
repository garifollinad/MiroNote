package com.example.mironote.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mironote.ui.menu.MenuActivity
import com.example.mironote.utils.Constants

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            val i = Intent(this, MenuActivity::class.java)
            startActivity(i)
            finish()
        }, Constants.DELAY_MILLIS)
    }
}