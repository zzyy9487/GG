package com.example.gg.Query

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.gg.R
import com.example.tt.Model.Data
import kotlinx.android.synthetic.main.cell_query.view.*

class QueryAdapter(var list:List<Data>, val context: Context?):RecyclerView.Adapter<QueryAdapter.ViewHolder>() {

    private var itemClickListener:clickedListener? = null

    interface clickedListener{
        fun saveQueryWords()
    }

    fun setclickedListener(checkedListener:clickedListener){
        this.itemClickListener = checkedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryAdapter.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.cell_query, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: QueryAdapter.ViewHolder, position: Int) {

        holder.bindViewHolder(list[position])

    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val imgView = itemView.imageView
        val textTitle = itemView.textTitle
        val textSay = itemView.textSay
        val textTime = itemView.textTime

        fun bindViewHolder(data:Data){

            Glide.with(itemView)
                .load(data.image)
                .transform(CircleCrop())
                .into(imgView)
            textTitle.text = data.title
            textSay.text = data.say
            textTime.text = data.time

            imgView.setOnClickListener {
                itemClickListener?.saveQueryWords()
            }

            textTitle.setOnClickListener {
                itemClickListener?.saveQueryWords()
            }

            textSay.setOnClickListener {
                itemClickListener?.saveQueryWords()
            }

            textTime.setOnClickListener {
                itemClickListener?.saveQueryWords()
            }

        }
    }
}
