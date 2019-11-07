package com.example.tt.Model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gg.R
import kotlinx.android.synthetic.main.cell.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class ListAdapter (var list: List<Data>, val context: Context?): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListAdapter.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.cell, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.bindViewHolder(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgView = itemView.imageView
        val textTitle = itemView.textTitle
        val textSay = itemView.textSay
        val textTime = itemView.textTime
        val textCount = itemView.textCount


        fun bindViewHolder(data: Data) {

            Glide.with(itemView)
                .load(data.image)
                .transform(CircleCrop())
                .into(imgView)
            textTitle.text = data.title
            textSay.text = data.say
            textTime.text = data.time
            textCount.text = data.count.toString()
            if(data.count == 0){itemView.textCount.visibility = View.INVISIBLE}
            else {itemView.textCount.visibility = View.VISIBLE}

        }
    }
}
