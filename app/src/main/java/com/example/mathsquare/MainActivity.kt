package com.example.mathsquare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login = findViewById<Button>(R.id.login)
        val play = findViewById<Button>(R.id.play_game)
        val user = findViewById<TextView>(R.id.logged_user)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser==null){
            user.setText("Login")
            login.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }else{
            user.setText(auth.currentUser?.displayName)
            login.setOnClickListener {
                val intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
            }
        }

        play.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val login = findViewById<Button>(R.id.login)
        val user = findViewById<TextView>(R.id.logged_user)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser==null){
            user.setText("Login")
            login.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }else{
            user.setText(auth.currentUser?.displayName)
            login.setOnClickListener {
                val intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
