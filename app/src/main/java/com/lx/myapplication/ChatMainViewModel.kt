package com.lx.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.lx.myapplication.ChatMessageEntity
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class ChatMainViewModel: ViewModel() {

    companion object {
        const val TAG = "ChatMainViewModel"
    }

//    private val chatRepository = ChatRepository(Dispatchers.IO)

    private var _chatMainLiveData = MutableLiveData<ChatMainState>(ChatMainState.UnInitialized)
    val chatMainLiveData: LiveData<ChatMainState> = _chatMainLiveData


    /*
    * 채팅 메세지 읽어오기
    * */
    fun fetchReadData(): Job = viewModelScope.launch {
        try {
            _chatMainLiveData.postValue(ChatMainState.Loading)
            var response: MutableList<ChatMessageEntity> = mutableListOf()
            val database = Firebase.database(CommonUtil.CHAT_DB_URL)
            CommonUtil.CHAT_REF = database.getReference(CommonUtil.CHAT_PATH).child(CommonUtil.CHAT_PATH_CHILD)
            CommonUtil.CHAT_REF.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for ((index, snapshot) in dataSnapshot.children.withIndex()) {
                        val chatItem = snapshot.value as HashMap<String, String>
                        var msgItem = ChatMessageEntity(chatItem["name"], chatItem["uid"], chatItem["content"], chatItem["timestamp"])
                        response.add(index, msgItem)
                    }

                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    _chatMainLiveData.postValue(ChatMainState.Error)
                }
            })
        } catch (e: Exception) {
            _chatMainLiveData.postValue(ChatMainState.Error)
        } finally {
        }
    }


    /*
    * 채팅 메세지 쓰기
    * */
    fun fetchWriteData(mName: String, mContent: String): Job = viewModelScope.launch {
        // [START write_message]
        // Write a message to the database

        val database = Firebase.database(CommonUtil.CHAT_DB_URL)
        CommonUtil.CHAT_REF = database.getReference(CommonUtil.CHAT_PATH).child(CommonUtil.CHAT_PATH_CHILD)

        val uuid = UUID.randomUUID().toString()
        var chatMessageEntity = ChatMessageEntity(mName, uuid, mContent, CommonUtil.getTime(System.currentTimeMillis()))

        CommonUtil.CHAT_REF.push().setValue(chatMessageEntity)   // 데이터가 계속 쌓이는 방식

        // [END write_message]


        fetchReadData()
    }

}