package com.example.twisterpm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.twisterpm.model.Comment
import com.example.twisterpm.model.LoginSignupRepository
import com.example.twisterpm.model.Message
import com.example.twisterpm.model.MessagesRepository

class MessagesViewModel : ViewModel() {

    //User auth
    private val loginSignupRepository = LoginSignupRepository()
    val userLiveData = loginSignupRepository.userLiveData
    val userErrorLiveData = loginSignupRepository.errorLiveData

    //Messages
    private val messagesRepository = MessagesRepository()
    val messageLiveData: LiveData<List<Message>> = messagesRepository.messagesLiveData
    val messageErrorLiveData: LiveData<String> = messagesRepository.errorMessageLiveData

    init {
        loadMessages()
    }

    fun loadMessages() {
        messagesRepository.getMessages()
    }

    operator fun get(index: Int): Message? {
        return messageLiveData.value?.get(index)
    }


//    fun addMessage(message: Message) {
//        messagesRepository.addMessage(message)
//    }
//    fun addComment(comment: Comment) {
//        messagesRepository.addComment(comment)
//    }
//
//    fun deleteMessage(id: Int) {
//        messagesRepository.deleteMessage(id)
//    }
//    fun deleteComment(id: Int) {
//        messagesRepository.deleteComment(id)
//    }


    //User Sign Out
    fun signOut() {
        loginSignupRepository.signOut()
    }
}