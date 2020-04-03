package com.example.mathsquare

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.view_question.view.*
import org.w3c.dom.Text
import kotlin.math.abs
import kotlin.math.pow

class HexQuestion @JvmOverloads constructor(
    context: Activity,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    input: String,
    s: Int
) : LinearLayout(context, attrs, defStyleAttr){

    var hexans: String = "00"
    var ans: Int = 0
    var score: Int = 0
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_question, this, true)
        orientation = HORIZONTAL
        hexans = input
        ans = toDecimal(hexans)
        score = s
        value.text = ans.toString()
    }

    fun toDecimal(hexans:String): Int {
        var tot: Int = 0
        for (x in hexans.length-1 downTo 0) {
            var m = 16.0
            m = m.pow(abs(x-hexans.length-1))
            tot += (toNumber(hexans[x]) * m).toInt()
        }
        return tot
    }

    fun toNumber(c:Char): Int{
        if(!c.isDigit()){
            when(c){
                'A'->return 10
                'B'->return 11
                'C'->return 12
                'D'->return 13
                'E'->return 14
                'F'->return 15
            }
        }else{
            return c.toInt()
        }
        return 0
    }

    fun checkCorrect():Boolean{
        var sum = 0
        if (b1.isChecked){
            sum+=1
        }
        if (b2.isChecked){
            sum+=2
        }
        if (b4.isChecked){
            sum+=4
        }
        if (b8.isChecked){
            sum+=8
        }
        if (b16.isChecked){
            sum+=16
        }
        if (b32.isChecked){
            sum+=32
        }
        if (b64.isChecked){
            sum+=64
        }
        if (b128.isChecked){
            sum+=128
        }
        return sum==ans
    }



}