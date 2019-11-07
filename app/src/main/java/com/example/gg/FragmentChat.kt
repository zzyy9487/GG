package com.example.gg

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tt.Model.Data
import com.example.tt.Model.ListAdapter
import kotlinx.android.synthetic.main.fragment_2.view.*
import android.app.Activity
import android.content.Intent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 */
class FragmentChat : Fragment() {

    lateinit var rootview: View
    lateinit var adapter : ListAdapter
    var inputList = mutableListOf<Data>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //判斷Bundle是否不為空
        val bundle = data?.extras?: return

        if(requestCode==1 && resultCode== Activity.RESULT_OK){

            val backlist = bundle.getString("databack")
            val gson = Gson()
            val typeTokenback = object : TypeToken<MutableList<Data>>() {

            }.type
            var backDataList: MutableList<Data> = gson.fromJson(backlist, typeTokenback)
            inputList.clear()
            inputList.addAll(backDataList)
            adapter.notifyDataSetChanged()

        }
    }

    var array = listOf(
        R.drawable.p0,
        R.drawable.p1,
        R.drawable.p2,
        R.drawable.p3,
        R.drawable.p4
    )

    fun additem() {
        if (inputList.isNullOrEmpty()) {
            for (i in 0..49) {
                var img = array.random()
                var title = "Title$i"
                var say = "Say$i"
                var time = "${String.format("%02d", Random.nextInt(0, 23))}" + ":" + (0..5).random().toString() + (0..9).random().toString()
                var count = (0..99).random()
                inputList.add(Data(img, "$title", "$say", "$time", count))
            }
        }
        val act = activity as MainActivity
        act.setbadge()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        additem()

        rootview = inflater.inflate(R.layout.fragment_2, container, false)
        adapter = ListAdapter(inputList, this.context)
        rootview.recyclerView.adapter = adapter
        rootview.recyclerView.layoutManager = LinearLayoutManager(this.context)

        rootview.toolbar.setOnMenuItemClickListener { item ->
            when (item?.itemId) {

                R.id.menu_allread -> {
                    AlertDialog.Builder(context!!)
                        .setTitle("要將所有訊息標為已讀嗎?!")
                        .setNeutralButton("取消") { dialog, which -> }
                        .setPositiveButton("標為已讀") { dialog, which ->
                            inputList.forEach { it.count = 0 }
                            adapter.notifyDataSetChanged()
                            val act = activity as MainActivity
                            act.removebadge()
                        }.show()
                }

                R.id.menu_order -> {
                    val array = arrayOf("收到的時間", "未讀訊息", "我的最愛")
                    AlertDialog.Builder(context!!)
                        .setItems(array, object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                when (which) {
                                    0 -> {
                                        sortByTime()
                                    }
                                    1 -> {
                                        sortByCount()
                                    }
                                    2 -> {
                                        Toast.makeText(context, "沒這東西XDDD", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }).show()
                }

                R.id.menu_edit -> {

                    val gson = Gson()
                    val typeTokengo = object : TypeToken<MutableList<Data>>() {

                    }.type
                    val intent = Intent(context, DeleteActivity::class.java)
                    val json = gson.toJson(inputList, typeTokengo)
                    intent.putExtra("datago", json)
                    startActivityForResult(intent, 1)
                }

                R.id.menu_search -> {

                    val gson = Gson()
                    val typeTokengo = object : TypeToken<MutableList<Data>>() {

                    }.type
                    val intent = Intent(context, QueryActivity::class.java)
                    val json = gson.toJson(inputList, typeTokengo)
                    intent.putExtra("dataquery", json)
                    startActivity(intent)
                }

                R.id.menu_talk -> {
                    inputList.clear()
                    adapter.notifyDataSetChanged()
                    val act = activity as MainActivity
                    act.removebadge()
                }
            }
            true
        }
        return rootview
    }

    private fun sortByCount() {
        inputList.sortWith(compareBy({ it.count }, { it.time }))
        adapter.notifyDataSetChanged()
    }

    private fun sortByTime() {
        inputList.sortBy { it.time }
        adapter.notifyDataSetChanged()
    }
}
