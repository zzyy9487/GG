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
import kotlinx.android.synthetic.main.activity_query.*
import kotlinx.android.synthetic.main.fragment_fragment_all.view.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentAll : Fragment() {

    lateinit var rootview :View
    lateinit var adapter: QueryAdapter
    val allInputList = mutableListOf<Data>()

    private var notifyQueryAdapter: notifyQueryAdapterListener? = null

    interface notifyQueryAdapterListener{
        fun notifyQueryAdapter()
    }

    fun setNotifyQueryAdapterListener(notifyQueryAdapterListener:notifyQueryAdapterListener){
        this.notifyQueryAdapter = notifyQueryAdapterListener
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val queryAct = activity as QueryActivity
        allInputList.clear()
        allInputList.addAll(queryAct.afterFilterList)

        rootview = inflater.inflate(R.layout.fragment_fragment_all, container, false)
        adapter = QueryAdapter(allInputList, this.context)
        rootview.recyclerViewall.adapter = adapter
        rootview.recyclerViewall.layoutManager = LinearLayoutManager(this.context)

        adapter.setclickedListener(object :QueryAdapter.clickedListener{

            override fun saveQueryWords() {
                var recordWords = queryAct.ed_Query.text
                queryAct.recordList.filter { it.word.contains("$recordWords", ignoreCase = true) }.isEmpty()

                if(queryAct.recordList.filter { it.word.contains("$recordWords", ignoreCase = true) }.isEmpty()){
                    queryAct.recordList.add(Record("$recordWords"))
                    notifyQueryAdapter?.notifyQueryAdapter()

                    queryAct.sqlDB.delete("recordList", null, null)
                    for (i in 0 until queryAct.recordList.size){
                        queryAct.sqlDB.execSQL("INSERT INTO recordList(position, words) VALUES(?,?)", arrayOf<Any?>(i, queryAct.recordList[i].word))
                    }

                }
                else{

                }
            }
        })

        return rootview
    }
}
