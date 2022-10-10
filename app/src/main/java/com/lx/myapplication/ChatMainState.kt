package com.lx.myapplication

import com.lx.myapplication.ChatMessageEntity

sealed class ChatMainState {

    object UnInitialized: ChatMainState()

    object Loading: ChatMainState()

    data class Success(
        val chatList: List<ChatMessageEntity>
    ): ChatMainState()

    object Error: ChatMainState()
}
