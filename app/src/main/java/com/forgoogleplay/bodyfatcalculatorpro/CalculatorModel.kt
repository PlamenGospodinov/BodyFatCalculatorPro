package com.forgoogleplay.bodyfatcalculatorpro

import kotlin.random.Random
import java.util.*

data class CalculatorModel(
    var id:Int = getAutoId(),
    var gender:String = "",
    var age:Int = 0,
    var weight:Int = 0,
    var height:Int = 0,
    var neck:Int = 0,
    var waist:Int = 0,
    var hip:Int = 0,
    var result:Float = 0.0f,
) {
    companion object{
        fun getAutoId():Int{
            val random = Random
            return random.nextInt(100)
        }
    }

}