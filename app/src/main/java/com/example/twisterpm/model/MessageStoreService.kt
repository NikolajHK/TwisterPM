package com.example.twisterpm.model

import retrofit2.Call
import  retrofit2.http.*

interface MessageStoreService {

    //Messages
    @GET("messages")
    fun getAllMessages():Call<List<Message>>

    @POST("messages")
    fun postMessage(@Body message: Message): Call<Message>

    @DELETE("messages/{id}")
    fun deleteMessage(@Path("id") id: Int): Call<Message>


    //Comments
    @GET("messages/{messageId}/comments")
    fun getCommentsByMessageId():Call<List<Comment>>

    @POST("messages/{messageId}/comments")
    fun postComment(@Body comment: Comment): Call<Comment>

    @DELETE("messages/{messageId}/comments/{commentId}")
    fun deleteComment(@Path("commentId") id: Int): Call<Comment>

}