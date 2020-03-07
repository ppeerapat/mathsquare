package com.example.mathsquare.data

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mathsquare.data.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    private var auth: FirebaseAuth
    init{
        auth = FirebaseAuth.getInstance()
    }

    fun login(username: String, password: String): Result<FirebaseUser> {

        auth.signInWithEmailAndPassword(username, password)

        if(auth.currentUser!=null){
            val user: FirebaseUser = auth.currentUser as FirebaseUser
            return Result.Success(user)
        }else{
            return Result.Error(IOException())
        }

    }

    fun logout() {
        // TODO: revoke authentication
        auth.signOut()
    }

    fun register(firstName: String,username: String, password: String): Result<FirebaseUser> {
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                }

                // ...
            }
        return login(username,password)

    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}

