package com.example.twisterpm.model

data class Comment(
    val content: String,
    val id: Int,
    val messageId: Int,
    val user: String
)