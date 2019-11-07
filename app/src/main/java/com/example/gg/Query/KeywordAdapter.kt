package com.example.gg.Query

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.gg.R
import kotlinx.android.synthetic.main.cell_keyword.view.*

class KeywordAdapter(var list:MutableList<Keyword>, val context: Context?):RecyclerView.Adapter<KeywordAdapter.ViewHolder>() {

    private val kItemDragHelperCallback = RecyclerViewItemTouchHelper()
    private val kItemTouchHelper = ItemTouchHelper(kItemDragHelperCallback)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        kItemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.cell_keyword, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindViewHolder(list[position])
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val textViewKeyword = itemView.textView_keyword

        fun bindViewHolder(keyword: Keyword){

            textViewKeyword.text = keyword.word

        }
    }

    inner class RecyclerViewItemTouchHelper : ItemTouchHelper.Callback(){

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            var swipe = 0
            var move = 0
            swipe = ItemTouchHelper.START or ItemTouchHelper.END
            move = ItemTouchHelper.UP  or ItemTouchHelper.DOWN or ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT

            return makeMovementFlags(move, swipe)
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
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition

            list.let {
                val from = list[fromPosition]
                list.removeAt(fromPosition)
                list.add(toPosition, from)
                notifyItemMoved(fromPosition, toPosition)
                return true
            }
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        }
    }
}
