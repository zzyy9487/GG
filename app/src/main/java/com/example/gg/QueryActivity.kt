package com.example.gg

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gg.Query.*
import com.example.tt.Model.Data
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_query.*
import java.util.prefs.Preferences

class QueryActivity : AppCompatActivity() {

    val gson = Gson()
    val queryInputList = mutableListOf<Data>()
    val afterFilterList = mutableListOf<Data>()
    val fragmentAll = FragmentAll()
    val manager = this.supportFragmentManager
    lateinit var keywordAdapter:KeywordAdapter
    var keywordList = mutableListOf<Keyword>()
    lateinit var recordAdapter:RecordAdapter
    val recordList = mutableListOf<Record>()
    val recorOffList = mutableListOf<Record>()
    var recordSwitchStatus :Int = 1
    lateinit var sqlDB: SQLiteDatabase

    val listener = SwipeRefreshLayout.OnRefreshListener {
        keywordList.clear()
        keywordListAdd()
        keywordAdapter.notifyDataSetChanged()
        swipe.isRefreshing = false
    }

    private fun keywordListAdd() {
        keywordList.add(Keyword("Kotlin"))
        keywordList.add(Keyword("AndroidStudio"))
        keywordList.add(Keyword("OrzOrzOrz"))
        keywordList.add(Keyword("2020"))
        keywordList.add(Keyword("Pocketful of Sunshine"))
        keywordList.add(Keyword("GoGoRo"))
        keywordList.add(Keyword("Nobody's Home"))
        keywordList.add(Keyword("MARVEL"))
        keywordList.add(Keyword("TAIWAN"))
        keywordList.shuffle()
    }

    private fun recordListAddSQLite(){
        val DB = sqlDB.rawQuery("SELECT * FROM recordList", null)
        if (DB.count == 0){

        }
        else {
            DB.moveToFirst()
            if (recordSwitchStatus == 1){
                for (i in 0 until DB.count) {
                    recordList.add(DB.getInt(0), Record(DB.getString(1)))
                    //移動到下一筆
                    DB.moveToNext()
                }
                recordAdapter.notifyDataSetChanged()
                DB.close()
            }
            else{
                for (i in 0 until DB.count) {
                    recorOffList.add(DB.getInt(0), Record(DB.getString(1)))
                    //移動到下一筆
                    DB.moveToNext()
                }
                DB.close()
            }
        }
    }

    private fun modifySwitchStatus(){
//        val switchDB = sqlDB.rawQuery("SELECT * FROM switchStatus", null)
//        switchDB.moveToFirst()
//        if (switchDB.count == 0){
//
//        }
//        else {
//            recordSwitchStatus = switchDB.getInt(1)
//        }

        val preference = getSharedPreferences("QQ", Context.MODE_PRIVATE)
        var switchStatus = preference.getString("switch", "")

        if (switchStatus.isNullOrEmpty()){

        }
        else{
            recordSwitchStatus = switchStatus.toInt()
        }



    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)

        // Layout

        val keywordRecordLayout = findViewById<ConstraintLayout>(R.id.keyrecordLayout)
        val tabfragLayout = findViewById<ConstraintLayout>(R.id.tabfragLayout)

        // keywordItem

        keywordListAdd()

        // 返回按鈕

        btn_back.setOnClickListener {
            this@QueryActivity.finish()
        }

        // 搜尋欄清除按鈕

        btn_clear.setOnClickListener{
            ed_Query.text.clear()
            btn_clear.visibility = View.INVISIBLE
            tabfragLayout.visibility = View.GONE
            keywordRecordLayout.visibility = View.VISIBLE
        }

        // Keyword

        keywordAdapter = KeywordAdapter(keywordList, this)
        val recyclerViewKeyword = findViewById<RecyclerView>(R.id.recyclerViewKeyWord)
        recyclerViewKeyword.layoutManager = StaggeredGridLayoutManager(3, 0)
        recyclerViewKeyword.adapter = keywordAdapter
        keywordAdapter.onAttachedToRecyclerView(recyclerViewKeyword)

        // Record

        recordAdapter = RecordAdapter(recordList, this)
        val recyclerViewRecord = findViewById<RecyclerView>(R.id.recyclerViewRecord)
        recyclerViewRecord.layoutManager = LinearLayoutManager(this)
        recyclerViewRecord.adapter = recordAdapter
        recordAdapter.onAttachedToRecyclerView(recyclerViewRecord)

        // Record Switch

        val textRecordSwitch = findViewById<TextView>(R.id.textViewRecordSwitch)
        textRecordSwitch.setOnClickListener {

            if (recordSwitchStatus == 1) {
                AlertDialog.Builder(this)
                    .setTitle("確定要停用自動記錄功能?")
                    .setNeutralButton("取消") { dialog, which -> }
                    .setPositiveButton("確定") { dialog, which ->
                        textRecordSwitch.text = "啟用自動記錄功能"
                        recordSwitchStatus = 0
//                        sqlDB.delete("switchStatus", null, null)
//                        sqlDB.execSQL("INSERT INTO switchStatus(position, status) VALUES(?,?)", arrayOf<Any?>(0, "0"))

                        val preference = getSharedPreferences("QQ", Context.MODE_PRIVATE)
                        val editor = preference.edit()
                        editor.putString("switch", "0")
                        editor.commit()

                        recorOffList.clear()
                        recorOffList.addAll(recordList)
                        recordList.clear()
                        recordAdapter.notifyDataSetChanged()
                        textViewDeleteAll.visibility = View.INVISIBLE
                        textViewl.visibility = View.INVISIBLE
                    }.show()
            }
            else{
                AlertDialog.Builder(this)
                    .setTitle("確定要啟用自動記錄功能?")
                    .setNeutralButton("取消") { dialog, which -> }
                    .setPositiveButton("確定") { dialog, which ->
                        textRecordSwitch.text = "停用自動記錄功能"
                        recordSwitchStatus = 1
//                        sqlDB.delete("switchStatus", null, null)
//                        sqlDB.execSQL("INSERT INTO switchStatus(position, status) VALUES(?,?)", arrayOf<Any?>(0, 1))

                        val preference = getSharedPreferences("QQ", Context.MODE_PRIVATE)
                        val editor = preference.edit()
                        editor.putString("switch", "1")
                        editor.commit()

                        recordList.clear()
                        recordList.addAll(recorOffList)
                        recorOffList.clear()
                        recordAdapter.notifyDataSetChanged()
                        textViewDeleteAll.visibility = View.VISIBLE
                        textViewl.visibility = View.VISIBLE
                    }.show()
            }
        }

        // Record Data All Remove

        val deleteAll = findViewById<TextView>(R.id.textViewDeleteAll)
        deleteAll.setOnClickListener {
            recordList.clear()
            recordAdapter.notifyDataSetChanged()
            sqlDB.delete("recordList", null, null)
        }

        // 接收資料

        val typeTokengo = object : TypeToken<MutableList<Data>>() {

        }.type
        val json = intent.getStringExtra("dataquery")
        val queryDataList: MutableList<Data> = gson.fromJson(json, typeTokengo)
        queryInputList.addAll(queryDataList)

        // Swipe Refreash Layout

        swipe.isEnabled = true
        swipe.setOnRefreshListener(listener)


        // Fragment 啟用加設定

        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragLayout, fragmentAll).commit()
        tabLayout.getTabAt(0)!!.select()

        // fragment.interface

        fragmentAll.setNotifyQueryAdapterListener(object :FragmentAll.notifyQueryAdapterListener{

            override fun notifyQueryAdapter() {

                if (recordSwitchStatus == 1) {
                    recordAdapter.notifyDataSetChanged()
                }
                else{

                }
            }
        })

        // recordAdapter.interface

        recordAdapter.setrecordDeleteListener(object :RecordAdapter.recordDeleteListener{

            override fun deleteRecord(position: Int) {
                if (recordSwitchStatus == 1) {
                    recordList.removeAt(position)
                    recordAdapter.notifyDataSetChanged()

                    sqlDB.delete("recordList", null, null)
                    for (i in 0 until recordList.size){
                        sqlDB.execSQL("INSERT INTO recordList(position, words) VALUES(?,?)", arrayOf<Any?>(i, recordList[i].word))
                    }
                }
                else{

                }
            }
        })

        ed_Query.addTextChangedListener{
            if (ed_Query.text.isEmpty()){
                afterFilterList.clear()
                fragmentAll.allInputList.clear()
                fragmentAll.adapter.notifyDataSetChanged()
                btn_clear.visibility = View.GONE
                tabfragLayout.visibility = View.GONE
                keywordRecordLayout.visibility = View.VISIBLE
            }
            else{
                btn_clear.visibility = View.VISIBLE
                tabfragLayout.visibility = View.VISIBLE
                keywordRecordLayout.visibility = View.GONE

                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                    override fun onTabSelected(tab: TabLayout.Tab) {

                        when (tab.position) {
                            0 -> {
                                afterFilterList.clear()
                                afterFilterList.addAll(queryInputList.filter { it.title.contains(ed_Query.text, ignoreCase = true)||it.say.contains(ed_Query.text, ignoreCase = true)||it.time.contains(ed_Query.text, ignoreCase = true) })
                                fragmentAll.allInputList.clear()
                                fragmentAll.allInputList.addAll(afterFilterList)
                                fragmentAll.adapter.notifyDataSetChanged()
                            }

                            1 -> {
                                afterFilterList.clear()
                                afterFilterList.addAll(queryInputList.filter { it.title.contains(ed_Query.text, ignoreCase = true) })
                                fragmentAll.allInputList.clear()
                                fragmentAll.allInputList.addAll(afterFilterList)
                                fragmentAll.adapter.notifyDataSetChanged()
                            }

                            2 -> {
                                afterFilterList.clear()
                                afterFilterList.addAll(queryInputList.filter { it.say.contains(ed_Query.text, ignoreCase = true) })
                                fragmentAll.allInputList.clear()
                                fragmentAll.allInputList.addAll(afterFilterList)
                                fragmentAll.adapter.notifyDataSetChanged()
                            }

                            3 -> {
                                afterFilterList.clear()
                                afterFilterList.addAll(queryInputList.filter { it.time.contains(ed_Query.text, ignoreCase = true) })
                                fragmentAll.allInputList.clear()
                                fragmentAll.allInputList.addAll(afterFilterList)
                                fragmentAll.adapter.notifyDataSetChanged()
                            }
                        }
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {

                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {

                    }
                })

                if (tabLayout.getTabAt(0)!!.isSelected){
                    afterFilterList.clear()
                    afterFilterList.addAll(queryInputList.filter { it.title.contains(ed_Query.text, ignoreCase = true)||it.say.contains(ed_Query.text, ignoreCase = true)||it.time.contains(ed_Query.text, ignoreCase = true) })
                    fragmentAll.allInputList.clear()
                    fragmentAll.allInputList.addAll(afterFilterList)
                    fragmentAll.adapter.notifyDataSetChanged()
                }
                else if (tabLayout.getTabAt(1)!!.isSelected){
                    afterFilterList.clear()
                    afterFilterList.addAll(queryInputList.filter { it.title.contains(ed_Query.text, ignoreCase = true) })
                    fragmentAll.allInputList.clear()
                    fragmentAll.allInputList.addAll(afterFilterList)
                    fragmentAll.adapter.notifyDataSetChanged()
                }
                else if (tabLayout.getTabAt(2)!!.isSelected){
                    afterFilterList.clear()
                    afterFilterList.addAll(queryInputList.filter { it.say.contains(ed_Query.text, ignoreCase = true) })
                    fragmentAll.allInputList.clear()
                    fragmentAll.allInputList.addAll(afterFilterList)
                    fragmentAll.adapter.notifyDataSetChanged()
                }
                else if (tabLayout.getTabAt(3)!!.isSelected){
                    afterFilterList.clear()
                    afterFilterList.addAll(queryInputList.filter { it.time.contains(ed_Query.text, ignoreCase = true) })
                    fragmentAll.allInputList.clear()
                    fragmentAll.allInputList.addAll(afterFilterList)
                    fragmentAll.adapter.notifyDataSetChanged()
                }

            }
        }

        // SQLite

        sqlDB = SQLiteHelper(this).writableDatabase

        modifySwitchStatus()

        if (recordSwitchStatus == 1){
            textRecordSwitch.text = "停用自動記錄功能"
            textViewDeleteAll.visibility = View.VISIBLE
            textViewl.visibility = View.VISIBLE
        }
        else{
            textRecordSwitch.text = "啟用自動記錄功能"
            textViewDeleteAll.visibility = View.INVISIBLE
            textViewl.visibility = View.INVISIBLE
        }

        recordListAddSQLite()

        textViewRecord.setOnClickListener {
            val DB = sqlDB.rawQuery("SELECT * FROM recordList", null)
            Snackbar.make(queryall,"${DB.count}", Snackbar.LENGTH_LONG ).show()
            DB.close()
        }
    }
}
