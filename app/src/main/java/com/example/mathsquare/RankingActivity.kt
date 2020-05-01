package com.example.mathsquare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RankingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        val back = findViewById<Button>(R.id.back_button)

        back.setOnClickListener {
            finish()
        }
    }
}
