package com.lx.myapplication

import com.google.firebase.database.DatabaseReference
import java.text.SimpleDateFormat

object CommonUtil {

    const val CHAT_DB_URL = "https://chat-3b0b0-default-rtdb.asia-southeast1.firebasedatabase.app/"
    const val CHAT_PATH= "chat"
    const val CHAT_PATH_CHILD = "messages"
    lateinit var CHAT_REF: DatabaseReference

    var userName = ""  // 로그인 사용자 이름

    fun getTime(timeStamp: Long): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")  // 시간이 24시간으로
//        val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")  // 시간이 12시간으로
        return format.format(timeStamp)
    }

}