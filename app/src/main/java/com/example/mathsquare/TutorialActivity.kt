package com.example.mathsquare

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class TutorialActivity : AppCompatActivity() {

    private lateinit var tutorialAnimation: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        val tutorialImage = findViewById<ImageView>(R.id.tutorial_gif).apply {
            setBackgroundResource(R.drawable.tutorial)
            tutorialAnimation = background as AnimationDrawable
        }

        tutorialImage.setOnClickListener({ tutorialAnimation.start() })
    }
}