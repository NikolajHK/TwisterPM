package com.example.twisterpm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    val content: String,
    val id: Int,
    val messageId: Int,
    val user: String
) : Parcelable {
    constructor(content: String, messageId: Int , user: String) : this(content, -1, messageId, user)
}