package com.forgoogleplay.bodyfatcalculatorpro

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception
import kotlin.math.log10

class SQLiteHelper(context:Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "calculator.db"
        private const val TBL_CALCULATOR = "tbl_calculator"
        private const val ID = "id"
        private const val GENDER = "gender"
        private const val AGE = "age"
        private const val WEIGHT = "weight"
        private const val HEIGHT = "height"
        private const val NECK = "neck"
        private const val WAIST = "waist"
        private const val HIP = "hip"
        private const val RESULT = "result"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblCalculator = ("CREATE TABLE " + TBL_CALCULATOR + "("
                + ID + " INTEGER PRIMARY KEY, " +
                GENDER + " TEXT," +
                AGE + " INTEGER," +
                WEIGHT + " INTEGER," +
                HEIGHT + " INTEGER," +
                NECK + " INTEGER," +
                WAIST + " INTEGER," +
                HIP + " INTEGER," +
                RESULT + " FLOAT" + ")")
        db?.execSQL(createTblCalculator)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_CALCULATOR")
        onCreate(db)
    }

    fun insertCalculator(std: CalculatorModel): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,std.id)
        contentValues.put(GENDER,std.gender)
        contentValues.put(AGE,std.age)
        contentValues.put(WEIGHT,std.weight)
        contentValues.put(HEIGHT,std.height)
        contentValues.put(NECK,std.neck)
        contentValues.put(WAIST,std.waist)
        contentValues.put(HIP,std.hip)
        contentValues.put(RESULT,std.result)

        val success = db.insert(TBL_CALCULATOR,null,contentValues)
        db.close()
        return success
    }

    fun getAllCalculators():ArrayList<CalculatorModel>{
        val stdList: ArrayList<CalculatorModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_CALCULATOR"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery,null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int?
        var gender:String
        var age:Int
        var weight:Int
        var height:Int
        var neck:Int
        var waist:Int
        var hip:Int
        var result:Float


        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"))
                age = cursor.getInt(cursor.getColumnIndexOrThrow("age"))
                weight = cursor.getInt(cursor.getColumnIndexOrThrow("weight"))
                height = cursor.getInt(cursor.getColumnIndexOrThrow("height"))
                neck = cursor.getInt(cursor.getColumnIndexOrThrow("neck"))
                waist = cursor.getInt(cursor.getColumnIndexOrThrow("waist"))
                hip = cursor.getInt(cursor.getColumnIndexOrThrow("hip"))
                result = cursor.getFloat(cursor.getColumnIndexOrThrow("result"))
                val std = CalculatorModel(id = id, gender = gender, age = age, weight = weight, height = height, neck = neck, waist = waist, hip = hip, result = result)
                stdList.add(std)
            }while(cursor.moveToNext())
        }

        return  stdList
    }

    fun updateCalculator(std:CalculatorModel): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID,std.id)
        contentValues.put(GENDER,std.gender)
        contentValues.put(AGE,std.age)
        contentValues.put(WEIGHT,std.weight)
        contentValues.put(HEIGHT,std.height)
        contentValues.put(NECK,std.neck)
        contentValues.put(WAIST,std.waist)
        contentValues.put(HIP,std.hip)

        var res:Float = 0.0f
        if(GENDER=="Male"){
            res =(495/(1.0324-0.19077*(log10(WAIST.toDouble()-NECK.toDouble())) + 0.15456*(log10(HEIGHT.toDouble()))) -450).toFloat()
        }
        else if(GENDER=="Female"){
            res =(495/(1.29579-0.35004*(log10(WAIST.toDouble()+ HIP.toDouble()-NECK.toDouble())) + 0.22100*(log10(HEIGHT.toDouble()))) -450).toFloat()
        }
        contentValues.put(RESULT,res)
        val success = db.update(TBL_CALCULATOR,contentValues,"id = " + std.id,null)
        db.close()
        return success

    }
}