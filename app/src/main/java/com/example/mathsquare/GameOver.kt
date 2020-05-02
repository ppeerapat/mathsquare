package com.example.mathsquare

import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathsquare.model.Ranking
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_ranking.*
import org.w3c.dom.Text


class GameOver : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        auth = FirebaseAuth.getInstance()
        val score = intent.getIntExtra("PLAYER_SCORE",0)
        val gamemode = intent.getIntExtra("GAMEMODE",0)

        val highscore = findViewById<TextView>(R.id.highscore)
        val newer = findViewById<TextView>(R.id.newhighscore)
        val score_text = findViewById<TextView>(R.id.score)

        score_text.text = score.toString()

        val submit = findViewById<Button>(R.id.submit_score)
        submit.isEnabled = false
        val database = FirebaseDatabase.getInstance().getReference("rankings"+gamemode.toString()).child(auth.currentUser?.uid!!)
        database.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                val highestScore = p0.child("score").getValue(Int::class.java)
                if(highestScore!=null){
                    if(score>highestScore!!){
                        newer.text = "New "+newer.text
                        highscore.text = score.toString()
                        submit.isEnabled = true
                    }else{
                        highscore.text = highestScore.toString()
                    }
                }else{
                    if(auth.currentUser!=null){
                        submit.isEnabled = true
                    }
                }
            }
        });


        if(auth.currentUser!=null) {

            submit.setOnClickListener {

                val database = FirebaseDatabase.getInstance().getReference("rankings"+gamemode.toString())

                val rank = Ranking(
                    0,
                    auth.currentUser?.uid!!,
                    score,
                    auth.currentUser?.displayName!!,
                    ""
                )
                database.child(auth.currentUser?.uid!!).setValue(rank).addOnCompleteListener{
                    Toast.makeText(this,getString(R.string.success_ranking),Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this,getString(R.string.fail_ranking),Toast.LENGTH_SHORT).show()
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
