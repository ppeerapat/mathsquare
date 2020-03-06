package com.example.mathsquare.data

import android.util.Log
import com.example.mathsquare.data.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}

