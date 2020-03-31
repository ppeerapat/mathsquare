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

class BinQuestion @JvmOverloads constructor(
    context: Activity,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr){

    val ans: Int = (1..8).random()

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_question, this, true)
        orientation = HORIZONTAL

        value.text = ans.toString()
        b1.setOnClickListener{
            removeCorrect(context)
        }
        b2.setOnClickListener{
            removeCorrect(context)
        }
        b4.setOnClickListener{
            removeCorrect(context)
        }
        b8.setOnClickListener{
            removeCorrect(context)
        }
        b16.setOnClickListener{
            removeCorrect(context)
        }
        b32.setOnClickListener{
            removeCorrect(context)
        }
        b64.setOnClickListener{
            removeCorrect(context)
        }
    }

    fun removeCorrect(context:Activity){
        val questionList = context.findViewById<LinearLayout>(R.id.ques_list)
        val score = context.findViewById<TextView>(R.id.score)
        if(checkCorrect()){
            questionList.removeViewAt(questionList.indexOfChild(this))
            score.text = (score.text.toString().toInt() + 10).toString()
        }
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
        return sum==ans
    }
}