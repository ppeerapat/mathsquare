package com.example.mathsquare

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    lateinit var submit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val displayName = findViewById<EditText>(R.id.display_name_input)
        val email = findViewById<EditText>(R.id.email_input)
        val password = findViewById<EditText>(R.id.password_input)
        val confirmPassword = findViewById<EditText>(R.id.confirm_password)

        submit = findViewById<Button>(R.id.register)

        auth = FirebaseAuth.getInstance()

        submit.setOnClickListener {

            submit.isEnabled = false
            val displayName = displayName.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()
            val confirmPassword = confirmPassword.text.toString()

            if(!isFilled(displayName)||!isEmail(email)||!isFilled(password)||(confirmPassword!=password)){
                Toast.makeText(this, "Information Invalid",Toast.LENGTH_SHORT).show()
                submit.isEnabled = true
            }else {
                register(
                    displayName,
                    email,
                    password
                )
            }
        }

        displayName.afterTextChanged{
            if(!isFilled(displayName.text.toString())){
                displayName.error = "Display Name is Blank"
            }else{
                displayName.error = null
            }

        }
        email.afterTextChanged {
            if(!isEmail(email.text.toString())){
                email.error = "Email is Incorrect"
            }else{
                email.error = null
            }
        }
        password.afterTextChanged {
            if(!isPassword(password.text.toString())){
                password.error = "Password must contain at least 6 characters"
            }else{
                password.error = null
            }
        }
        confirmPassword.afterTextChanged {
            if(confirmPassword.text.toString()!=password.text.toString()){
                confirmPassword.error = "Confirm password is incorrect"
            }else{
                confirmPassword.error = null
            }
        }
    }

    fun isFilled(input:String): Boolean {
        return !input.isBlank()
    }
    fun isPassword(input:String): Boolean {
        return input.length>=6
    }
    fun isEmail(input:String):Boolean{
        return if (input.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(input).matches()
        } else {
            false
        }
    }

    private fun register(displayName:String,email:String,password:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(!it.isSuccessful)return@addOnCompleteListener
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build()
                auth.currentUser?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener{
                        if(!it.isSuccessful)return@addOnCompleteListener
                        Toast.makeText(this, "Successfully created User, Welcome: ${auth.currentUser?.displayName}", Toast.LENGTH_SHORT).show()
                        auth.signOut()
                        finish()
                    }
            }
            .addOnFailureListener{
                Toast.makeText(this, "Failed to create User: ${it.message}",Toast.LENGTH_SHORT).show()
                submit.isEnabled=true
            }

    }

}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}