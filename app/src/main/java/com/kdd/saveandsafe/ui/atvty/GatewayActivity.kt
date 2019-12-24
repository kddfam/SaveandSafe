package com.kdd.saveandsafe.ui.atvty

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kdd.saveandsafe.R

class GatewayActivity : AppCompatActivity() {

    // Views Declaration
    lateinit var mMoneyManager : Button
    lateinit var mTimeManager : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gateway)

        // Views Initialization
        mMoneyManager = findViewById(R.id.moneyboxing)
        mTimeManager = findViewById(R.id.timeboxing)

        // Views Click Handler
        mMoneyManager.setOnClickListener { openMoneyManager() }
        mTimeManager.setOnClickListener { openTimeManager() }
    }

    private fun openMoneyManager() {
        val intent = Intent(this@GatewayActivity, MoneyManagement::class.java)
        startActivity(intent)
    }

    private fun openTimeManager() {
        val intent1 = Intent(this@GatewayActivity, TimeManagement::class.java)
        startActivity(intent1)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
