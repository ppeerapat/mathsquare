package com.example.mathsquare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_main.view.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.email_input)
        val password = findViewById<EditText>(R.id.password_input)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.register)

        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            login(email.text.toString(),password.text.toString())
        }

    }

    fun login(email:String,password:String){
        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "Please fill Email and Password", Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(!it.isSuccessful)return@addOnCompleteListener
                Toast.makeText(this, "Successfully logged in, Welcome: ${it.result?.user?.displayName}", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener{
                Toast.makeText(this, "Failed to logged in: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

}