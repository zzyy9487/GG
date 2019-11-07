package com.example.gg

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_delete.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tt.Model.Data
import com.example.tt.Model.DeleteAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DeleteActivity : AppCompatActivity() {

    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        val typeTokengo = object : TypeToken<MutableList<Data>>() {

        }.type
        val json = intent.getStringExtra("datago")
        val goDataList: MutableList<Data> = gson.fromJson(json, typeTokengo)

        var deleteInputList = mutableListOf<Data>()

        deleteInputList.addAll(goDataList)


        var adapter = DeleteAdapter(deleteInputList, this)

        adapter.setcheckedListener(object :DeleteAdapter.checkedListener{
            override fun modifytrue(position: Int) {
                deleteInputList[position].ischecked = true
            }

            override fun modifyfalse(position:Int){
                deleteInputList[position].ischecked = false
            }
        })

        recyclerView2.layoutManager = LinearLayoutManager(this)
        recyclerView2.adapter = adapter

        btn_delete.setOnClickListener{

            deleteInputList.removeAll(deleteInputList.filter { it.ischecked == true })
            adapter.notifyDataSetChanged()

            val bundle = Bundle()
            val typeTokenback = object : TypeToken<MutableList<Data>>() {

            }.type
            val json2 = gson.toJson(deleteInputList, typeTokenback)
            bundle.putString("databack", json2)
            setResult(Activity.RESULT_OK, Intent().putExtras(bundle))
            this@DeleteActivity.finish()
        }

        toolbar2.setOnMenuItemClickListener { item ->
            when(item?.itemId){
                R.id.menu_back->{
                    this@DeleteActivity.finish()
                }
            }
            true
        }
    }
}
