package com.forgoogleplay.bodyfatcalculatorpro



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.log10

class ProActivity: AppCompatActivity() {
    private lateinit var calculate_Button: Button
    private lateinit var calcAdd: Button
    private lateinit var viewBtn: Button
    private lateinit var updateBtn: Button
    private lateinit var resetButton: Button
    private lateinit var editAge: EditText
    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var editNeck: EditText
    private lateinit var editWaist: EditText
    private lateinit var editHip: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var category: String
    private lateinit var selectedRadioButton: RadioButton
    private lateinit var sqliteHelper:SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter:CalculatorAdapter? = null
    private var std:CalculatorModel? = null


    private fun addCalculator(){
        var gender: Int = 0
        var genderStr: String = "Male"
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
                        val std = CalculatorModel(gender = genderStr,age = age.toInt(),weight = weight.toInt(),height = height.toInt(),neck = neck.toInt(),waist = waist.toInt(),hip = hip.toInt(),result = bodyFat.toFloat())
                        val status = sqliteHelper.insertCalculator(std)
                        if(status > -1){
                            val toast = Toast.makeText(applicationContext,"Calculation added...",Toast.LENGTH_SHORT)
                            toast.show()
                            ClearText()
                            getCalculations()
                        }
                        else{
                            val toast = Toast.makeText(applicationContext,"Calculation not saved...",Toast.LENGTH_SHORT)
                            toast.show()
                        }
                        /*val intent = Intent(this, ProResultActivity::class.java)
                        intent.putExtra("key",bodyFatRes)
                        intent.putExtra("cat",cat)
                        startActivity(intent)*/
                    }

                }


            }
        }
    }


    private fun initView(){

        editAge = findViewById(R.id.ageEdit)
        editWeight = findViewById(R.id.weightEdit)
        editHeight = findViewById(R.id.heightEdit)
        editNeck = findViewById(R.id.neckEdit)
        editWaist = findViewById(R.id.waistEdit)
        editHip = findViewById(R.id.hipEdit)
        radioGroup = findViewById(R.id.genderGroup)
        calculate_Button = findViewById(R.id.calculate_button)
        calcAdd = findViewById(R.id.calcAdd)
        viewBtn = findViewById(R.id.viewBtn)
        updateBtn = findViewById(R.id.updateBtn)
        resetButton = findViewById(R.id.resetBtn)
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun declareVars(){
        var gender: Int = 0
        var genderStr: String = "Male"

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pro_activity)
        initView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)
        calcAdd.setOnClickListener{addCalculator()}
        calculate_Button.setOnClickListener {declareVars()}
        viewBtn.setOnClickListener{getCalculations()}
        updateBtn.setOnClickListener{updateCalculator()}
        adapter?.setOnClickItem {
            Toast.makeText(this,it.age.toString(),Toast.LENGTH_SHORT).show()
            /*val selectedRadioButtonId: Int = radioGroup.checkedRadioButtonId
            selectedRadioButton = findViewById(selectedRadioButtonId)*/
            /*val value = it.gender
            if(value == "Male"){
                radioGroup.check(radio_male)
            }*/

            editAge.setText(it.age)
            editWeight.setText(it.weight)
            editHeight.setText(it.height)
            editNeck.setText(it.neck)
            editWaist.setText(it.waist)
            editHip.setText(it.hip)

            std = it

        }
        adapter?.setOnClickDeleteItem {
            deleteCalculator(it.id)
            getCalculations()

        }
        resetButton.setOnClickListener{
            ClearText()
        }

    }

    private fun deleteCalculator(id:Int){

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this record?")
        builder.setCancelable(true)
        getCalculations()
        builder.setPositiveButton("Yes"){dialog,_ ->
            sqliteHelper.deleteById(id)
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){dialog,_ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
        getCalculations()
    }

    private fun updateCalculator() {
        var gender: Int = 0
        var genderStr: String = "Male"
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
        val age = editAge.text.toString().toDouble()
        val weight = editWeight.text.toString().toDouble()
        val height = editHeight.text.toString().toDouble()
        val neck = editNeck.text.toString().toDouble()
        val waist = editWaist.text.toString().toDouble()
        val hip = editHip.text.toString().toDouble()
        //genderStr == std?.gender &&
        if(age.toInt() == std?.age && weight.toInt() == std?.weight &&
        height.toInt() == std?.height && neck.toInt() == std?.neck &&
                waist.toInt() == std?.waist && hip.toInt() == std?.hip)
                {

            Toast.makeText(this,"Record not changed...",Toast.LENGTH_LONG).show()
            return
        }
        val heightInMeters: Double = height.toDouble() / 100
        val heightInSecond: Double = heightInMeters * heightInMeters
        val bmi: Double = weight / heightInSecond
        val bmiSecond: Double = bmi * bmi
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


        if(std == null){
            return
        }

        val std = CalculatorModel(id = std!!.id, gender = genderStr,age = age.toInt(),
                weight = weight.toInt(),height = height.toInt(),
                neck = neck.toInt(),waist = waist.toInt(),
                hip = hip.toInt(),result = bodyFat.toFloat())
        val status = sqliteHelper.updateCalculator(std)

        if(status > -1){
            ClearText()
            getCalculations()
        }
        else{
            Toast.makeText(this,"Update failed...",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCalculations(){
        val stdList = sqliteHelper.getAllCalculators()
        Log.e("pppp","${stdList.size}")

        adapter?.addItems(stdList)
    }

    private fun ClearText(){
        editAge.setText("")
        editHeight.setText("")
        editWeight.setText("")
        editHip.setText("")
        editWaist.setText("")
        editNeck.setText("")
        editAge.requestFocus()

    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CalculatorAdapter()
        recyclerView.adapter = adapter
    }
}
