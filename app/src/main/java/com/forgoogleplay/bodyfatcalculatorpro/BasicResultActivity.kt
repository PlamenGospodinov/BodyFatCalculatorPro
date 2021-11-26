package com.forgoogleplay.bodyfatcalculatorpro

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BasicResultActivity : AppCompatActivity() {
    lateinit var resultView: TextView
    lateinit var string: String
    lateinit var category: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basic_result_activity)

        val actionBar = supportActionBar
        actionBar!!.title = "Result"
        actionBar.setDisplayHomeAsUpEnabled(true)
        val extras = intent.extras
        var result: Double = 2.0
        if (extras != null) {
            result = extras.getDouble("key")
            category = extras.getString("cat").toString()
            //The key argument here must match that used in the other activity
        }

        resultView = findViewById(R.id.percentage)
        string = "${result} % \n body fat. \n Category: \n ${category}"
        resultView.text = string

    }
}