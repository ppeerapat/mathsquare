package com.example.mathsquare

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mathsquare.model.BinQuestion
import kotlinx.android.synthetic.main.view_question.view.*
import org.w3c.dom.Text
import kotlin.random.Random


class GameActivity : AppCompatActivity() {

    lateinit var postQuestion: Handler
    var difficulty: Int = 0
    var gamemode: Int = 0
    var spawnTime: Long = 3000
    private var score: Int = 0
    private val scorePerQ = arrayListOf(10,20,30)
    val MINIMUM_SPAWN_TIME: Long = 400
    val DURATION_PENALTY = 10
    val MAX_QUESTIONS = 8
    val easy = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 32, 33, 34, 64, 65, 66, 128, 129, 130)
    val medium = arrayListOf(19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45,
        46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 85, 127, 170, 240, 255)
    val hard = arrayListOf(67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 86, 87, 88, 89, 90, 91,
        92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115,
        116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142,
        143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165,
        166, 167, 168, 169, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189,
        190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212,
        213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235,
        236, 237, 238, 239, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254)

    private val post = object : Runnable {
        override fun run() {
            createQuestion()
            postQuestion.postDelayed(this, spawnTime)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        difficulty = intent.getIntExtra("difficulty",0)
        gamemode = intent.getIntExtra("gamemode",0)

        println(difficulty)
        println(gamemode)
        if(gamemode==1){
            val show128 = findViewById<TextView>(R.id.show128)
            val show64 = findViewById<TextView>(R.id.show64)
            val show32 = findViewById<TextView>(R.id.show32)
            val show16 = findViewById<TextView>(R.id.show16)
            show128.text = "8"
            show64.text= "4"
            show32.text= "2"
            show16.text= "1"
        }


        createQuestion()
        postQuestion = Handler(Looper.getMainLooper())
        //println(pickHex())
    }
    fun createQuestion(){
        val questionList = findViewById<LinearLayout>(R.id.ques_list)

        if(questionList.childCount ==MAX_QUESTIONS){
            postQuestion.removeCallbacks(post)
            val intent = Intent(this, GameOver::class.java)
            intent.putExtra("PLAYER_SCORE",score)
            intent.putExtra("GAMEMODE",gamemode)
            startActivity(intent)
            finish()
        }else {
            val d = diffNumber()

            var b: BinQuestion? = null
            //System.out.println(gamemode)
            if(gamemode==0){
                b = BinQuestion(
                    this,
                    input = pickNumber(d).toString(),
                    s = scorePerQ[d],
                    isHex = false
                )
            }else{
                b = BinQuestion(
                    this,
                    input = pickHex(),
                    s = scorePerQ[0],
                    isHex = true
                )
            }

            b.b1.setOnClickListener {
                removeCorrect(b)
            }
            b.b2.setOnClickListener {
                removeCorrect(b)
            }
            b.b4.setOnClickListener {
                removeCorrect(b)
            }
            b.b8.setOnClickListener {
                removeCorrect(b)
            }
            b.b16.setOnClickListener {
                removeCorrect(b)
            }
            b.b32.setOnClickListener {
                removeCorrect(b)
            }
            b.b64.setOnClickListener {
                removeCorrect(b)
            }
            b.b128.setOnClickListener {
                removeCorrect(b)
            }
            questionList.addView(b, questionList.childCount)
        }
    }
    fun removeCorrect(b: BinQuestion){
        val questionList = findViewById<LinearLayout>(R.id.ques_list)
        val score_text = findViewById<TextView>(R.id.score)
        if(b.checkCorrect()){
            questionList.removeView(b)
            score+=b.score
            if(spawnTime>MINIMUM_SPAWN_TIME) {
                spawnTime -= (difficulty+1)*DURATION_PENALTY
            }
            score_text.text = score.toString()
        }
        if(questionList.childCount==0){
            createQuestion()
        }
    }

    fun diffNumber():Int {
        val r = Random.nextDouble()
        if (r < .8 - difficulty * 0.2) {
            return 0
        } else if (r < .95 - difficulty * 0.12) {
            return 1
        } else {
            return 2
        }
    }
    fun pickNumber(diff:Int):Int{
        when (diff){
            0-> {
                return easy[(0..easy.size-1).random()]
            }
            1-> {
                return medium[(0..medium.size-1).random()]
            }
            2-> {
                return hard[(0..hard.size-1).random()]
            }
        }
        return 0
    }
    fun pickHex():String{
        val hex:ArrayList<String> = arrayListOf("1","2","3","4","5","6","7","8","9","A","B","C","D","E","F")
        return hex[(0..hex.size-1).random()]+hex[(0..hex.size-1).random()]
    }
    override fun onPause() {
        super.onPause()
        postQuestion.removeCallbacks(post)
    }
    override fun onResume() {
        super.onResume()
        postQuestion.post(post)
    }



}
