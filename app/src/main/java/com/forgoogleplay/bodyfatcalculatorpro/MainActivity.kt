package com.forgoogleplay.bodyfatcalculatorpro

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToBasicActivity(view: View?) {
        val intent = Intent(this, BasicActivity::class.java)
        startActivity(intent)
    }

    fun goToProActivity(view: View?) {
        val intent = Intent(this, ProActivity::class.java)
        startActivity(intent)
    }
}