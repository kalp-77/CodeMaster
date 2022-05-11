package com.example.mastercoderapp.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mastercoderapp.R
import com.example.mastercoderapp.data.model.ContestDetailsItem
import com.example.mastercoderapp.ui.activities.WebActivity
import com.example.mastercoderapp.ui.fragments.CodeChefFragment
import com.example.mastercoderapp.ui.fragments.LeetCodeFragment

class LeetCodeAdapter (private val context: LeetCodeFragment, private val articles: List<ContestDetailsItem>) : RecyclerView.Adapter<LeetCodeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context.requireContext()).inflate(R.layout.leet_code_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.contestName.text = article.name
        holder.start.text = "On: " + article.startTime.subSequence(0,10)
        holder.end.text = "At: " + article.startTime.subSequence(11,16)
        holder.duration.text = "Duration: " + (article.duration.toInt()%86400) / 3600 + " hrs"

        holder.itemView.setOnClickListener {
            val intent = Intent(context.requireContext(), WebActivity::class.java)
            intent.putExtra("URL",article.url)
            context.startActivity(intent)
        }
//        holder.itemView.startAnimation(animation)
//        holder.share.setOnClickListener{
//            val intent = Intent(Intent.ACTION_SEND)
//            intent.type = "text/plain"
//            val link :String = article.links.website
//            val body = "Look at this !! $link"
//            intent.putExtra(Intent.EXTRA_TEXT,link)
//            intent.putExtra(Intent.EXTRA_TEXT,body)
//            context.startActivity(Intent.createChooser(intent,"share"))
//        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var contestName: TextView = itemView.findViewById(R.id.contestName)
        var start : TextView = itemView.findViewById(R.id.start)
        var end : TextView = itemView.findViewById(R.id.end)
        var duration : TextView = itemView.findViewById(R.id.duration)
    }
}