package com.example.mathsquare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathsquare.model.Ranking
import kotlinx.android.synthetic.main.activity_ranking.*

class RankingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        val back = findViewById<Button>(R.id.back_button)

        val mock = test(20)

        ranking_recycler.adapter = RankingAdapter(mock)
        ranking_recycler.layoutManager = LinearLayoutManager(this)
        ranking_recycler.setHasFixedSize((true))

        back.setOnClickListener {
            finish()
        }
    }

    private fun test(size:Int): List<Ranking>{
        val list = ArrayList<Ranking>()

        for (i in 0 until size){
            val item = Ranking(i,"0",10,"test"+i.toString(),"Chicken")
            list+=item
        }
        return list
    }
}
