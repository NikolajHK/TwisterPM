package com.example.twisterpm.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class MessagesRepository {

    private val url = "https://anbo-restmessages.azurewebsites.net/api/"

    private val messageStoreService: MessageStoreService
    val messagesLiveData: MutableLiveData<List<Message>> = MutableLiveData<List<Message>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()

    val commentsLiveData: MutableLiveData<List<Comment>> = MutableLiveData<List<Comment>>()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        messageStoreService = build.create(MessageStoreService::class.java)
        getMessages()
    }

    fun getMessages() {
        messageStoreService.getAllMessages().enqueue(object : Callback<List<Message>> {
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                if (response.isSuccessful) {
                    messagesLiveData.postValue(response.body())
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " +response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("Fail", message)
                }
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("Fail", t.message!!)
            }

        })
    }

    fun addMessage() {
        TODO()
    }

    fun addComment() {
        TODO()
    }
    fun deleteMessage() {
        TODO()
    }
    fun deleteComment() {
        TODO()
    }



}