package com.example.twisterpm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    val id: Int,
    val content: String,
    val user: String,
    val totalComments: Int
) : Parcelable {
    constructor(content: String, user: String) : this(-1, content, user, 0)

    override fun toString(): String {
        return "$user  $content"
    }
}