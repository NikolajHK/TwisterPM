package com.example.twisterpm.model

data class Message(
    val content: String,
    val id: Int,
    val totalComments: Int,
    val user: String
)