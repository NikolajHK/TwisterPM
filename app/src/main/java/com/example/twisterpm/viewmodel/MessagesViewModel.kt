package com.example.twisterpm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.twisterpm.model.Comment
import com.example.twisterpm.model.LoginSignupRepository
import com.example.twisterpm.model.Message
import com.example.twisterpm.model.MessagesRepository

class MessagesViewModel : ViewModel() {

    //User auth
    private val userRepository = LoginSignupRepository()
    val userLiveData = userRepository.userLiveData
    val userLoggedOutLiveData = userRepository.loggedOutLiveData
    val userErrorLiveData = userRepository.errorLiveData

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


    fun add(message: Message) {
        messagesRepository.add(message)
    }
    fun add(comment: Comment) {
        messagesRepository.add(comment)
    }

    fun delete(id: Int) {
        messagesRepository.delete(id)
    }


    //User Sign Out
    fun signOut() {
        userRepository.signOut()
    }
}