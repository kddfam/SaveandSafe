package com.kdd.saveandsafe.ui.atvty

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kdd.saveandsafe.R
import java.util.*

class MainActivity : AppCompatActivity() {

    // Variable Declarations
    lateinit var mTimer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Timer Functionality
        mTimer = Timer()
        mTimer.schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(this@MainActivity, GatewayActivity::class.java)
                startActivity(intent)
            }
        },1000)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
