package com.example.mathsquare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mathsquare.model.Ranking
import kotlinx.android.synthetic.main.ranking_card.view.*
import org.w3c.dom.Text

class RankingAdapter(private val rankingList: List<Ranking>) : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ranking_card,parent,false)
        return RankingViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val currentItem = rankingList[position]

        holder.number.text = currentItem.id.toString()
        holder.name.text = currentItem.name
        holder.score.text = currentItem.score.toString()
        holder.rank.text = currentItem.rank
    }
    override fun getItemCount() = rankingList.size

    class RankingViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val number: TextView = itemView.number
        val name: TextView = itemView.name
        val score: TextView = itemView.score
        val rank: TextView = itemView.rank

    }
}