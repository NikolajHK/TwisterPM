package com.example.twisterpm.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twisterpm.databinding.FragmentMessagethreadBinding
import com.example.twisterpm.model.Comment
import com.example.twisterpm.model.CommentsAdapter
import com.example.twisterpm.viewmodel.LoginSignupViewModel
import com.example.twisterpm.viewmodel.MessagesViewModel

class MessageThreadFragment : Fragment() {

    private var _binding: FragmentMessagethreadBinding? = null
    private val binding get() = _binding!!
    private val messagesViewModel : MessagesViewModel by activityViewModels()
    private val loginSignupViewModel : LoginSignupViewModel by activityViewModels()
    private val args: MessageThreadFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMessagethreadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val selectedMessage = messagesViewModel.selectedMessage
        Log.d("MessageThreadFragment","Selected Message is: $selectedMessage")
        binding.messageUserTextview.text = selectedMessage?.user
        binding.messageContentTextview.text = selectedMessage?.content

        messagesViewModel.loadComments(selectedMessage.id)
        Log.d("MessageThreadFragment","loaded comments")


        fun reloadComments() {
            messagesViewModel.loadComments(selectedMessage.id)
            Log.d("MessageThreadFragment","reloaded comments")
        }

        messagesViewModel.commentsLiveData.observe(viewLifecycleOwner) { comments ->
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (comments == null) View.GONE else View.VISIBLE
            if (comments != null) {
                val adapter = CommentsAdapter(comments) { position ->
                    messagesViewModel.selectedComment = comments[position]
                    Log.d("MessageThreadFragment", "Comment position: $position")

                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }
        binding.buttonDeleteMessage.setOnClickListener {
            loginSignupViewModel.userLiveData.observe(viewLifecycleOwner) { firebaseUser ->
                if (firebaseUser?.email == messagesViewModel.selectedMessage.user) {
                    Log.d(
                        "MessageThreadFragment",
                        "Logged in Firebase User: ${firebaseUser.email} is similar to message poster: ${messagesViewModel.selectedMessage.user}"
                    )
                    messagesViewModel.deleteMessage(messagesViewModel.selectedMessage.id)
                    Log.d("MessageThreadFragment","deleted $messagesViewModel.selectedMessage")
                    val action = MessageThreadFragmentDirections.actionMessageThreadFragmentToMessageStreamFragment()
                    findNavController().popBackStack()
                } else {
                    val log = "Failed to delete comment"
                    Log.d("MessageStreamFragment", log)
                }
            }
        }

        binding.buttonDeleteComment.setOnClickListener {
            loginSignupViewModel.userLiveData.observe(viewLifecycleOwner) { firebaseUser ->
                if (firebaseUser?.email == selectedMessage.user) {
                    Log.d(
                        "MessageThreadFragment",
                        "Logged in Firebase User: ${firebaseUser.email} is similar to message poster: ${selectedMessage.user}"
                    )
                    messagesViewModel.deleteComment(selectedMessage.id, messagesViewModel.selectedComment.id)
                    Log.d("MessageThreadFragment","deleted $messagesViewModel.selectedComment")
                    reloadComments()
                }
                if (firebaseUser?.email == messagesViewModel.selectedComment.user) {
                    Log.d(
                        "MessageThreadFragment",
                        "Logged in Firebase User: ${firebaseUser.email} is similar to comment poster: ${messagesViewModel.selectedComment.user}"
                    )
                    messagesViewModel.deleteComment(selectedMessage.id, messagesViewModel.selectedComment.id)
                    Log.d("MessageThreadFragment","deleted $messagesViewModel.selectedComment")
                    reloadComments()
                } else {
                    val log = "Failed to delete comment"
                    Log.d("MessageStreamFragment", log)
                }
            }
        }

        binding.buttonNewComment.setOnClickListener {
            loginSignupViewModel.userLiveData.observe(viewLifecycleOwner) {firebaseUser ->
                if (firebaseUser != null) {
                    Log.d("MessageThreadFragment", "firebaseUser != null")
                    val content = binding.newCommentTextEdit.text.toString().trim()
                    val messageId = selectedMessage.id
                    val user = firebaseUser.email.toString().trim()
                    val newComment = Comment(content, messageId, user)
                    messagesViewModel.postComment(messageId, newComment)
                    Log.d("MessageThreadFragment", "post $newComment")
                    reloadComments()
                    binding.newCommentTextEdit.text.clear()
                } else {
                    val log = "Failed to add new message"
                    Log.d("MessageThreadFragment",log)
                }
            }
        }


        binding.swipeRefresh.setOnRefreshListener{
            messagesViewModel.loadComments(selectedMessage.id)
            Log.d("MessageThreadFragment","swipe refresh: Comments reloaded")
            binding.swipeRefresh.isRefreshing = false

        }


    }
}