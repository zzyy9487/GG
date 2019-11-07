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
import kotlinx.android.synthetic.main.fragment_fragment_title.view.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentTitle : Fragment() {

    lateinit var rootview :View
    lateinit var Adapter: QueryAdapter
    val titleInputList = mutableListOf<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val act = activity as QueryActivity
        titleInputList.clear()
        titleInputList.addAll(act.afterFilterList)

        rootview = inflater.inflate(R.layout.fragment_fragment_title, container, false)
        Adapter = QueryAdapter(titleInputList, this.context)
        rootview.recyclerViewtitle.adapter = Adapter
        rootview.recyclerViewtitle.layoutManager = LinearLayoutManager(this.context)
        Adapter.notifyDataSetChanged()

        return rootview
    }


}
