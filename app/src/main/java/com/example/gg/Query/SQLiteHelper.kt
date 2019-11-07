package com.example.gg.Query

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, name, null, version) {
    companion object {
        private const val name = "sqlite.db" //資料庫名稱
        private const val version = 1 //資料庫版本
    }

    override fun onCreate(db: SQLiteDatabase) {
        //建立資料表『myTable』，包含一個book字串欄位和一個price整數欄位
        db.execSQL("CREATE TABLE recordList(position integer PRIMARY KEY, words text NOT NULL)")
        db.execSQL("CREATE TABLE switchStatus(position integer PRIMARY KEY, status integer NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //刪除資料表
        db.execSQL("DROP TABLE IF EXISTS recordList")
        //重建資料庫
        onCreate(db)
    }
}
