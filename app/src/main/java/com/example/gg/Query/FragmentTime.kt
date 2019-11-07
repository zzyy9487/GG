package com.example.gg.Query


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gg.QueryActivity

import com.example.gg.R
import com.example.tt.Model.Data
import kotlinx.android.synthetic.main.fragment_fragment_time.view.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 */
class FragmentTime : Fragment() {

    lateinit var rootview :View
    lateinit var adapter: QueryAdapter
    val timeInputList = mutableListOf<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val act = activity as QueryActivity
        timeInputList.clear()
        timeInputList.addAll(act.afterFilterList)

        rootview = inflater.inflate(R.layout.fragment_fragment_time, container, false)
        adapter = QueryAdapter(timeInputList, this.context)
        rootview.recyclerViewtime.adapter = adapter
        rootview.recyclerViewtime.layoutManager = LinearLayoutManager(this.context)
        adapter.notifyDataSetChanged()

        return rootview
    }


}
