package com.example.tt.Model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gg.R
import kotlinx.android.synthetic.main.cell_delete.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class DeleteAdapter (var list: List<Data>, val context: Context?): RecyclerView.Adapter<DeleteAdapter.ViewHolder>() {

    private var modifyCheckedListener: checkedListener? = null

    interface checkedListener{
        fun modifytrue(position:Int)
        fun modifyfalse(position: Int)

    }

    fun setcheckedListener(checkedListener:checkedListener){
        this.modifyCheckedListener = checkedListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DeleteAdapter.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.cell_delete, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(dholder: DeleteAdapter.ViewHolder, position: Int) {
        dholder.bindViewHolder(list[position])
        dholder.itemView.checkBox.isChecked = list[position].ischecked

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView = itemView.imageView2
        val textTitle = itemView.textTitle2
        val textSay = itemView.textSay2
        val testTime = itemView.textTime2
        val checkBox = itemView.checkBox

        fun bindViewHolder(data: Data) {

            Glide.with(itemView)
                .load(data.image)
                .transform(CircleCrop())
                .into(imageView)
            textTitle.text = data.title
            textSay.text = data.say
            testTime.text = data.time
            checkBox.setOnClickListener {
                if (checkBox.isChecked){modifyCheckedListener?.modifytrue(position)}
                else{modifyCheckedListener?.modifyfalse(position)}
            }
        }
    }
}
