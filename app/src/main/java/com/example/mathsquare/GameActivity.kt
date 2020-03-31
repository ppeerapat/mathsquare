package com.example.mathsquare

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get


class GameActivity : AppCompatActivity() {

    lateinit var postQuestion: Handler

    var spawnTime: Long = 3000

    private val post = object : Runnable {
        override fun run() {
            createQuestion()
            postQuestion.postDelayed(this, spawnTime)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)



        val questionList = findViewById<LinearLayout>(R.id.ques_list)
        questionList.addView(BinQuestion(this),0)
        postQuestion = Handler(Looper.getMainLooper())
    }
    fun createQuestion(){
        val questionList = findViewById<LinearLayout>(R.id.ques_list)
        if(questionList.childCount ==5){
            postQuestion.removeCallbacks(post)
            finish()
        }else {
            questionList.addView(BinQuestion(this), 0)
        }
    }


    override fun onPause() {
        super.onPause()
        postQuestion.removeCallbacks(post)
    }
    override fun onResume() {
        super.onResume()
        postQuestion.post(post)
    }



}
