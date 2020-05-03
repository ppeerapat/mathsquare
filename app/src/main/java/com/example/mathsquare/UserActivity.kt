package com.example.mathsquare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

//UserActivity.kt
//
//Declares the user activity class with methods used for the user activity
//
//Peerapat Potch-a-nant
//
//March - April 2020

class UserActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        auth = FirebaseAuth.getInstance()

        val back = findViewById<Button>(R.id.back_button)
        val logout = findViewById<Button>(R.id.logout)
        val resetScore = findViewById<Button>(R.id.reset_score)
        val user = findViewById<TextView>(R.id.user)

        user.setText(getString(R.string.wel)+" "+auth.currentUser?.displayName+" !")
        resetScore.setOnClickListener {
            val uid:String = auth.currentUser?.uid!!

            val hex = FirebaseDatabase.getInstance().getReference("rankings1").child(uid)
            hex.removeValue()
            val dec = FirebaseDatabase.getInstance().getReference("rankings0").child(uid)
            dec.removeValue().addOnCompleteListener{
                Toast.makeText(this,"Reset Successful", Toast.LENGTH_SHORT).show()
            }
        }
        logout.setOnClickListener {
            auth.signOut()
            finish()
        }
        back.setOnClickListener {
            finish()
        }
    }
}
