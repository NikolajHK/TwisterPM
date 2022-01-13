package com.example.twisterpm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.twisterpm.model.Comment
import com.example.twisterpm.model.LoginSignupRepository
import com.example.twisterpm.model.Message
import com.example.twisterpm.model.MessagesRepository
import com.example.twisterpm.view.MessageThreadFragment

class MessagesViewModel : ViewModel() {

    private val messagesRepository = MessagesRepository()
    val messagesLiveData: LiveData<List<Message>> = messagesRepository.messagesLiveData
    val commentsLiveData: LiveData<List<Comment>> = messagesRepository.commentsLiveData
    val messageErrorLiveData: LiveData<String> = messagesRepository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = messagesRepository.updateMessageLiveData
    lateinit var selectedComment: Comment
    lateinit var selectedMessage: Message


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

}