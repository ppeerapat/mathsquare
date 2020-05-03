package com.example.mathsquare

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathsquare.model.Ranking
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_game_over.*
import kotlinx.android.synthetic.main.activity_ranking.*
import kotlinx.android.synthetic.main.game_popup.*

class RankingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        val back = findViewById<Button>(R.id.back_button)
        val decimalRanking = findViewById<Button>(R.id.decimal_ranking)
        val hexRanking = findViewById<Button>(R.id.hex_ranking)
        val a:List<Ranking> = arrayListOf<Ranking>()
        loadRanking(0,this)
        decimalRanking.setOnClickListener {
            ranking_recycler.adapter = RankingAdapter(a)
            ranking_recycler.layoutManager = LinearLayoutManager(this)
            loadRanking(0,this)
        }
        hexRanking.setOnClickListener {
            ranking_recycler.adapter = RankingAdapter(a)
            ranking_recycler.layoutManager = LinearLayoutManager(this)
            loadRanking(1,this)
        }

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

    private fun loadRanking(gamemode:Int,context: Context): List<Ranking>{
        val list = ArrayList<Ranking>()

        val database = FirebaseDatabase.getInstance().getReference("rankings"+gamemode.toString()).orderByChild("score")
        database.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    for(r in p0.children){
                        val name:String = r.child("name").getValue(String::class.java)!!
                        val score:Int = r.child("score").getValue(Int::class.java)!!
                        val ranking: Ranking = Ranking(0,"",score,name,"")
                        list.add(ranking)
                    }
                    var ranker:Int = 0
                    for (i in list.size-1 downTo 0){
                        var rank:String =""

                        when {
                            ranker<1 -> rank = "BigBrain"
                            ranker<2 -> rank = "MediumBrain"
                            ranker<3 -> rank = "SmallBrain"
                        };
                        list.get(i).id = ranker+1
                        list.get(i).rank=rank
                        ranker++
                    }
                    ranking_recycler.adapter = RankingAdapter(list.reversed())
                    ranking_recycler.layoutManager = LinearLayoutManager(context)
                }
            }
        });
        return list
    }
}
