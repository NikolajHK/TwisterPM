package com.example.twisterpm.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MessagesRepository {

    private val url = "https://anbo-restmessages.azurewebsites.net/api/"

    private val messageService: MessageService
    val messagesLiveData: MutableLiveData<List<Message>> = MutableLiveData<List<Message>>()
    val commentsLiveData: MutableLiveData<List<Comment>> = MutableLiveData<List<Comment>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        messageService = build.create(MessageService::class.java)
        getMessages()
    }

    fun getMessages() {
        messageService.getAllMessages().enqueue(object : Callback<List<Message>> {
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                if (response.isSuccessful) {
                    messagesLiveData.postValue(response.body())
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " +response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("MessageRepository", message)
                }
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("MessageRepository", t.message!!)
            }

        })
    }

    fun getCommentsByMessageId(messageId: Int) {
        messageService.getCommentsByMessageId(messageId).enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    commentsLiveData.postValue(response.body())
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " +response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("MessageRepository", message)
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("MessageRepository", t.message!!)
            }
        })
    }

    fun postMessage(message: Message) {
        messageService.postMessage(message).enqueue(object : Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                if (response.isSuccessful) {
                    Log.d("MessageRepository","Added: " + response.body())
                    updateMessageLiveData.postValue("Added: " + response.body())
                } else {
                    val log = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(log)
                    Log.d("MessageRepository", log)
                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("MessageRepository", t.message!!)
            }
        })
    }

    fun postComment(messageId: Int, comment: Comment) {
        messageService.postComment(messageId, comment).enqueue(object : Callback<Comment> {
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if (response.isSuccessful) {
                    Log.d("MessageRepository","Added: " + response.body())
                    updateMessageLiveData.postValue("Added: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("MessageRepository", message)
                }
            }

            override fun onFailure(call: Call<Comment>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("MessageRepository", t.message!!)
            }
        })
    }
    fun deleteMessage(id: Int) {
        messageService.deleteMessage(id).enqueue(object: Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                if (response.isSuccessful) {
                    Log.d("MessageRepository", "Updated: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("MessageRepository", message)
                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("MessageRepository", t.message!!)
            }

        })
    }

    fun deleteComment(messageId: Int, id: Int) {
        messageService.deleteComment(messageId, id).enqueue(object: Callback<Comment> {
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if (response.isSuccessful) {
                    Log.d("MessageRepository", "Updated: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("MessageRepository", message)
                }
            }

            override fun onFailure(call: Call<Comment>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("MessageRepository", t.message!!)
            }
        })
    }



}