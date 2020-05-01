package com.example.mathsquare

import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mathsquare.model.Ranking
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class GameOver : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        auth = FirebaseAuth.getInstance()
        val score = intent.getIntExtra("PLAYER_SCORE",0)
        val gamemode = intent.getIntExtra("GAMEMODE",0)

        val score_text = findViewById<TextView>(R.id.score)

        score_text.text = "Total Score: "+score.toString()

        val submit = findViewById<Button>(R.id.submit_score)
        if(auth.currentUser!=null) {
            submit.isEnabled = true
            submit.setOnClickListener {

                val database = FirebaseDatabase.getInstance().getReference("rankings"+gamemode.toString())

                val rankingId = database.push().key as String
                val rank = Ranking(
                    auth.currentUser?.uid,
                    score,
                    auth.currentUser?.displayName
                )
                database.child(rankingId).setValue(rank).addOnCompleteListener{
                    Toast.makeText(this,"Ranking Saved Successfully",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this,"Failed to save Ranking",Toast.LENGTH_SHORT).show()
                    submit.isEnabled = true
                }
                submit.isEnabled = false
            }
        }else{
            submit.isEnabled = false
        }

        val exit = findViewById<Button>(R.id.exit)

        exit.setOnClickListener {
            finish()
        }
    }
}
