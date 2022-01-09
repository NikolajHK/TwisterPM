package com.example.twisterpm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.twisterpm.model.Comment
import com.example.twisterpm.model.LoginSignupRepository
import com.example.twisterpm.model.Message
import com.example.twisterpm.model.MessagesRepository

class MessagesViewModel : ViewModel() {

//    //User auth
//    private val loginSignupRepository = LoginSignupRepository()
//    val userLiveData = loginSignupRepository.userLiveData
//    val userErrorLiveData = loginSignupRepository.errorLiveData


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

    fun loadComments(messageId: Int) {
        messagesRepository.getCommentsByMessageId(messageId)
    }

    operator fun get(index: Int): Message? {
        return messagesLiveData.value?.get(index)
    }

//    operator fun get(index: Int): Comment? {
//        return commentsLiveData.value?.get(index)
//    }

    fun postMessage(message: Message) {
        messagesRepository.postMessage(message)
    }
    fun postComment(messageId: Int, comment: Comment) {
        messagesRepository.postComment(messageId, comment)
    }

    fun deleteMessage(id: Int) {
        messagesRepository.deleteMessage(id)
    }
    fun deleteComment(messageId: Int, id: Int) {
        messagesRepository.deleteComment(messageId, id)
    }


//    //User Sign Out
//    fun signOut() {
//        loginSignupRepository.signOut()
//    }
}