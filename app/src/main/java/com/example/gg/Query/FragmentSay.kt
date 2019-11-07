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
import kotlinx.android.synthetic.main.fragment_fragment_say.view.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentSay : Fragment() {

    lateinit var rootview :View
    lateinit var adapter: QueryAdapter
    val sayInputList = mutableListOf<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val act = activity as QueryActivity
        sayInputList.clear()
        sayInputList.addAll(act.afterFilterList)

        rootview = inflater.inflate(R.layout.fragment_fragment_say, container, false)
        adapter = QueryAdapter(sayInputList, this.context)
        rootview.recyclerViewsay.adapter = adapter
        rootview.recyclerViewsay.layoutManager = LinearLayoutManager(this.context)
        adapter.notifyDataSetChanged()

        return rootview
    }


}
