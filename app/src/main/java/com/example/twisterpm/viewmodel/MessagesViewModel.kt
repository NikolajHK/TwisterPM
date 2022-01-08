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
    val messagesLiveData: LiveData<List<Message>> = messagesRepository.messagesLiveData
    val commentsLiveData: LiveData<List<Comment>> = messagesRepository.commentsLiveData
    val messageErrorLiveData: LiveData<String> = messagesRepository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = messagesRepository.updateMessageLiveData

    init {
        loadMessages()
    }

    fun loadMessages() {
        messagesRepository.getMessages()
    }

//    fun loadComments() {
//        messagesRepository.getCommentsByMessageId()
//    }

    operator fun get(index: Int): Message? {
        return messagesLiveData.value?.get(index)
    }

//    operator fun get(index: Int): Comment? {
//        return commentsLiveData.value?.get(index)
//    }

    fun postMessage(message: Message) {
        messagesRepository.postMessage(message)
    }
//    fun postComment(comment: Comment) {
//        messagesRepository.postComment(comment)
//    }

    fun deleteMessage(id: Int) {
        messagesRepository.deleteMessage(id)
    }
//    fun deleteComment(id: Int) {
//        messagesRepository.deleteComment(id)
//    }


    //User Sign Out
    fun signOut() {
        loginSignupRepository.signOut()
    }
}