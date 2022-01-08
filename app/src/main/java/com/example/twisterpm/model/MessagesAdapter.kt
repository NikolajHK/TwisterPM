package com.example.twisterpm.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twisterpm.R
import com.google.android.material.card.MaterialCardView
import com.example.twisterpm.model.Message

class MessagesAdapter(
    private val messages: List<Message>,
    private val onItemClicked: (position: Int) -> Unit
) :
    RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {


    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MessagesViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.message_item, viewGroup, false)
        return MessagesViewHolder(view, message, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: MessagesViewHolder, position: Int) {
        val message = messages[position]
        viewHolder.message =message
        viewHolder.userTextView.text = message.user
        viewHolder.contentTextView.text = message.content
        viewHolder.messagesContainer.animation = AnimationUtils.loadAnimation(viewHolder.itemView.context, R.anim.alpha)
    }


    inner class MessagesViewHolder(itemView: View, var message: Message? = null, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val userTextView: TextView = itemView.findViewById(R.id.user_textview)
        val contentTextView: TextView = itemView.findViewById(R.id.content_textview)
        val messagesContainer: MaterialCardView = itemView.findViewById(R.id.message_container)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            onItemClicked(position)
            val selectedMessage =
            message(message)
        }
    }

}