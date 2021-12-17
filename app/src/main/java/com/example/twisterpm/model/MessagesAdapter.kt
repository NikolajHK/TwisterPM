package com.example.twisterpm.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twisterpm.R
import com.google.android.material.card.MaterialCardView

class MessagesAdapter<Message>(
    private val items: List<Message>,
    private val onItemClicked: (position: Int) -> Unit
) :
    RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return MessagesViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: MessagesViewHolder, position: Int) {
        viewHolder.userTextView.text = items[position].toString()
        viewHolder.item_contrainer.contentDescription = items[position].toString()
        viewHolder.item_contrainer.animation = AnimationUtils.loadAnimation(viewHolder.itemView.context, R.anim.alpha)
    }


    class MessagesViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val userTextView: TextView = itemView.findViewById(R.id.user_textview)
        val contentTextView: TextView = itemView.findViewById(R.id.content_textview)
        val item_contrainer: MaterialCardView = itemView.findViewById(R.id.item_container)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}