package com.example.mathsquare.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mathsquare.R
import com.example.mathsquare.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val loggedUser = findViewById<TextView>(R.id.logged_user)
        val login = findViewById<Button>(R.id.login)

        if(auth.currentUser!=null){
            login.isEnabled = false
            var curr: FirebaseUser = auth.currentUser as FirebaseUser
            loggedUser.setText(curr.uid)
        }



        login.setOnClickListener{

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }

}