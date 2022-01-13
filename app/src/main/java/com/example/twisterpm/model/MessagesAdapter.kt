package com.example.twisterpm.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twisterpm.R
import com.google.android.material.card.MaterialCardView

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
        return MessagesViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: MessagesViewHolder, position: Int) {
        val message = messages[position]
        viewHolder.message = message
        viewHolder.itemUserTextView.text = message.user
        viewHolder.itemContentTextView.text = message.content
        viewHolder.itemContainer.animation = AnimationUtils.loadAnimation(viewHolder.itemView.context, R.anim.alpha)
    }


    inner class MessagesViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit, var message: Message? = null) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val itemDeleteButton: ImageButton = itemView.findViewById(R.id.item_delete)
        val itemUserTextView: TextView = itemView.findViewById(R.id.message_user_textview)
        val itemContentTextView: TextView = itemView.findViewById(R.id.message_content_textview)
        val itemContainer: MaterialCardView = itemView.findViewById(R.id.message_container)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }

}