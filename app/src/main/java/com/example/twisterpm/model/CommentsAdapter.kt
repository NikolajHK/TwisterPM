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

class CommentsAdapter(
    private val comments: List<Comment>,
    private val onItemClicked: (position: Int) -> Unit
) :
    RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.message_item, viewGroup, false)
        return CommentsViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: CommentsViewHolder, position: Int) {
        val comment = comments[position]
        viewHolder.comment = comment
        viewHolder.itemUserTextView.text = comment.user
        viewHolder.itemContentTextView.text = comment.content
        viewHolder.itemContainer.animation = AnimationUtils.loadAnimation(viewHolder.itemView.context, R.anim.alpha)
//        viewHolder.itemDeleteButton.setOnClickListener{
//            messagesViewModel.deleteComment(comment.messageId, comment.id)
//        }
    }


    inner class CommentsViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit, var comment: Comment? = null) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val itemDeleteButton: ImageButton = itemView.findViewById(R.id.item_delete)
        val itemUserTextView: TextView = itemView.findViewById(R.id.message_user_textview)
        val itemContentTextView: TextView = itemView.findViewById(R.id.message_content_textview)
        val itemContainer: MaterialCardView = itemView.findViewById(R.id.message_container)

        init {
            itemView.setOnClickListener(this)
//            itemDeleteButton.setOnClickListener {
//                messagesViewModel.deleteComment(comment.messageId, comment.id)
//            }
        }

//        fun deleteComment(comment: Comment, messagesViewModel: MessagesViewModel) {
//            messagesViewModel.deleteComment(comment.messageId, comment.id)
//        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }

//    fun deleteComment(comment: Comment, messagesViewModel: MessagesViewModel) {
//        messagesViewModel.deleteComment(comment.messageId, comment.id)
//    }
}