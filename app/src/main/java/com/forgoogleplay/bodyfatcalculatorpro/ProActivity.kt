package com.forgoogleplay.bodyfatcalculatorpro



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.log10

class ProActivity: AppCompatActivity() {
    lateinit var calculate_Button: Button
    lateinit var resetButton: Button
    lateinit var editAge: EditText
    lateinit var editWeight: EditText
    lateinit var editHeight: EditText
    lateinit var editNeck: EditText
    lateinit var editWaist: EditText
    lateinit var editHip: EditText
    lateinit var radioGroup: RadioGroup
    lateinit var category: String
    lateinit var selectedRadioButton: RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pro_activity)
        editAge = findViewById(R.id.ageEdit)
        editWeight = findViewById(R.id.weightEdit)
        editHeight = findViewById(R.id.heightEdit)
        editNeck = findViewById(R.id.neckEdit)
        editWaist = findViewById(R.id.waistEdit)
        editHip = findViewById(R.id.hipEdit)
        radioGroup = findViewById(R.id.genderGroup)
        calculate_Button = findViewById(R.id.calculate_button)
        resetButton = findViewById(R.id.resetBtn)
        var gender: Int = 0
        var genderStr: String = "Male"
        calculate_Button.setOnClickListener {
            val selectedRadioButtonId: Int = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                selectedRadioButton = findViewById(selectedRadioButtonId)
                val valuee: String = selectedRadioButton.text.toString()
                if (valuee == "Male") {
                    gender = 0
                    genderStr = "Male"
                } else {
                    gender = 1
                    genderStr = "Female"
                }
            }




            if (TextUtils.isEmpty(editAge.text.toString())) {
                val text = "Enter your age!"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()

            } else if (TextUtils.isEmpty(editWeight.text.toString())) {
                val text = "Enter your weight!"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()

            } else if (TextUtils.isEmpty(editHeight.text.toString())) {
                val text = "Enter your height!"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()

            } else if (editAge.toString().trim().isNotEmpty()) {
                var ageCheck = editAge.text.toString().toDouble()
                if (ageCheck <= 10 || ageCheck > 120) {
                    val text = "Enter a valid age!"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()

                } else if (editWeight.toString().trim().isNotEmpty()) {
                    var weightCheck = editWeight.text.toString().toDouble()
                    if (weightCheck <= 35 || weightCheck > 200) {
                        val text = "Enter a valid weight!"
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()
                    } else if (editHeight.toString().trim().isNotEmpty()) {
                        var heightCheck = editHeight.text.toString().toDouble()
                        if (heightCheck <= 130 || heightCheck > 230) {
                            val text = "Enter a valid height!"
                            val duration = Toast.LENGTH_SHORT
                            val toast = Toast.makeText(applicationContext, text, duration)
                            toast.show()

                        } else {
                            var age = editAge.text.toString().toDouble()
                            var weight = editWeight.text.toString().toDouble()
                            var height = editHeight.text.toString().toDouble()
                            var neck = editNeck.text.toString().toDouble()
                            var waist = editWaist.text.toString().toDouble()
                            var hip = editHip.text.toString().toDouble()
                            var heightInMeters: Double = height.toDouble() / 100
                            var heightInSecond: Double = heightInMeters * heightInMeters
                            var bmi: Double = weight / heightInSecond
                            var bmiSecond: Double = bmi * bmi
                            var bodyFat: Double = 1.0
                            if(genderStr=="Male"){
                                bodyFat =495/(1.0324-0.19077*(log10(waist-neck)) + 0.15456*(log10(height))) -450
                            }
                            else if(genderStr=="Female"){
                                bodyFat =495/(1.29579-0.35004*(log10(waist+hip-neck)) + 0.22100*(log10(height))) -450
                            }
                            val df = DecimalFormat("#.##")
                            category = "Obese"
                            df.roundingMode = RoundingMode.CEILING
                            val bodyFatRes = df.format(bodyFat).toDouble()
                            if ((bodyFatRes >= 25 && genderStr == "Male") || (bodyFatRes >= 32 && genderStr == "Female")) {
                                category = "Obese"
                                //progressBar.setProgressTintList(ColorStateList.valueOf(Color.BLACK))
                            } else if ((bodyFatRes >= 18 && bodyFatRes < 25 && genderStr == "Male") || (bodyFatRes < 32 && bodyFatRes >= 25 && genderStr == "Female")) {
                                category = "Average"
                                //progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED))
                            } else if ((bodyFatRes >= 14 && bodyFatRes < 18 && genderStr == "Male") || (bodyFatRes < 25 && bodyFatRes >= 21 && genderStr == "Female")) {
                                category = "Fitness"
                                // progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN))
                            } else if ((bodyFatRes >= 6 && bodyFatRes < 14 && genderStr == "Male") || (bodyFatRes < 21 && bodyFatRes >= 14 && genderStr == "Female")) {
                                category = "Athlete"
                                // progressBar.setProgressTintList(ColorStateList.valueOf(Color.CYAN))
                            } else if ((bodyFatRes >= 2 && bodyFatRes < 5.1 && genderStr == "Male") || (bodyFatRes < 14 && bodyFatRes >= 10 && genderStr == "Female")) {
                                category = "Essential Fat"
                                //progressBar.setProgressTintList(ColorStateList.valueOf(Color.WHITE))
                            }
                            var cat = category
                            val intent = Intent(this, ProResultActivity::class.java)
                            intent.putExtra("key",bodyFatRes)
                            intent.putExtra("cat",cat)
                            startActivity(intent)
                        }

                    }


                }
            }
        }

        resetButton.setOnClickListener{
            editAge.setText("")
            editHeight.setText("")
            editWeight.setText("")
            editHip.setText("")
            editWaist.setText("")
            editNeck.setText("")
        }

    }
}
