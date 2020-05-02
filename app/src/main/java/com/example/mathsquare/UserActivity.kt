package com.example.mathsquare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class UserActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        auth = FirebaseAuth.getInstance()

        val logout = findViewById<Button>(R.id.logout)
        val user = findViewById<TextView>(R.id.user)

        user.setText(getString(R.string.wel)+" "+auth.currentUser?.displayName+" !")

        logout.setOnClickListener {
            auth.signOut()
            finish()
        }
    }
}
