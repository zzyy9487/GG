package com.example.gg.Query

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.gg.R
import kotlinx.android.synthetic.main.cell_record.view.*

class RecordAdapter(var list:MutableList<Record>, val context: Context?):RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    private val rItemDragHelperCallback = RecyclerViewItemTouchHelper()
    private val rItemTouchHelper = ItemTouchHelper(rItemDragHelperCallback)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            rItemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private var recordDelete:recordDeleteListener? = null

    interface recordDeleteListener{
        fun deleteRecord(position:Int)
    }

    fun setrecordDeleteListener(recordDeleteListener:recordDeleteListener){
        this.recordDelete = recordDeleteListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.cell_record, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindViewHolder(list[position])

        holder.deletebotton.setOnClickListener {
            recordDelete?.deleteRecord(position)
        }
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val textViewRecord = itemView.textView_record
        val deletebotton = itemView.imageViewDelete

        fun bindViewHolder(record: Record){

            textViewRecord.text = record.word

        }
    }

    inner class RecyclerViewItemTouchHelper : ItemTouchHelper.Callback(){

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            var swipe = 0
            var move = 0
            swipe = androidx.recyclerview.widget.ItemTouchHelper.START or androidx.recyclerview.widget.ItemTouchHelper.END
            move = androidx.recyclerview.widget.ItemTouchHelper.UP  or androidx.recyclerview.widget.ItemTouchHelper.DOWN

            return androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags(move, swipe)
        }

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            recordDelete?.deleteRecord(position)
        }

    }
}
