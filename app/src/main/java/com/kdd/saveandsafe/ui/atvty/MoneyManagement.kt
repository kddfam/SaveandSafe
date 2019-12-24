package com.kdd.saveandsafe.ui.atvty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kdd.saveandsafe.R

class MoneyManagement : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_management)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
