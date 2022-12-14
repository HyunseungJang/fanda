package com.lx.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context?) :
    SQLiteOpenHelper(context,"Login.db",null,1) {
    override fun onCreate(MyDB: SQLiteDatabase) {
        MyDB.execSQL("create Table users(username TEXT primary Key, password Test)")
    }

    override fun onUpgrade(MyDB: SQLiteDatabase, i: Int, i1: Int) {
        MyDB.execSQL("drop Table if exists users")
    }

    fun insertData(username: String?, password: String?): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val result = MyDB.insert("users", null, contentValues)
        return if (result == 1L) false else true
    }

    fun checkUserName(username: String): Boolean {
        val MyDB = this.writableDatabase
        var res = true
        val cursor = MyDB.rawQuery("Select * from users where username =?", arrayOf(username))
        if (cursor.count <= 0) res = false
        return res
    }

    fun checkUserpass(username: String, password: String): Boolean {
        val MyDB = this.writableDatabase
        var res = true
        val cursor = MyDB.rawQuery(
            "Select * from users where username =? and password = ?",
            arrayOf(username, password)
        )
        if (cursor.count <= 0) res = false
        return res
    }

    companion object {
        const val DBNAME = "Login.db"
    }
}