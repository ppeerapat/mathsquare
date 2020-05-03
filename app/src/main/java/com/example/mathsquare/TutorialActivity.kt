package com.example.mathsquare

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

//TutorialActivity.kt
//
//Declares the tutorial activity class with methods used for the tutorial activity
//
//Tanyarin Karuchit, modified by Chanaporn Chaisumritchoke
//
//April 2020

class TutorialActivity : AppCompatActivity() {

    private lateinit var tutorialAnimation: AnimationDrawable

    //initialize instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        val back = findViewById<Button>(R.id.back_button)
        val tutorialImage = findViewById<ImageView>(R.id.tutorial_gif).apply {
            setBackgroundResource(R.drawable.tutorial)
            tutorialAnimation = background as AnimationDrawable
        }

        tutorialImage.setOnClickListener({ tutorialAnimation.start() })
        back.setOnClickListener {
            finish()
        }
    }
}