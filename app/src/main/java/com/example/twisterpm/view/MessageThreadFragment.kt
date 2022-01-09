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
import com.example.twisterpm.R
import com.example.twisterpm.databinding.FragmentMessagethreadBinding
import com.example.twisterpm.model.Comment
import com.example.twisterpm.model.CommentsAdapter
import com.example.twisterpm.model.Message
import com.example.twisterpm.model.MessagesAdapter
import com.example.twisterpm.viewmodel.LoginSignupViewModel
import com.example.twisterpm.viewmodel.MessagesViewModel

class MessageThreadFragment : Fragment() {

    private var _binding: FragmentMessagethreadBinding? = null
    private val binding get() = _binding!!
    private val messagesViewModel : MessagesViewModel by activityViewModels()
    private val loginSignupViewModel : LoginSignupViewModel by activityViewModels()
    private val args: MessageThreadFragmentArgs by navArgs()
    private lateinit var position: Unit
    private lateinit var message: Message
    private lateinit var comment: Comment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        message = args.message
//        comment = args.comment
        _binding = FragmentMessagethreadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO()
        val bundle = requireArguments()
        val messageThreadFragmentArgs
        : MessageThreadFragmentArgs = MessageThreadFragmentArgs.fromBundle(bundle)
        val position = messageThreadFragmentArgs.position
        val message = messagesViewModel.get(position)

        binding.messageUserTextview.text = message?.user
        binding.messageContentTextview.text = message?.content

        messagesViewModel.loadComments(position)
        Log.d("MessageThreadFragment","loaded comments")

        messagesViewModel.commentsLiveData.observe(viewLifecycleOwner) { comments ->
            if (comments != null) {
                val adapter = CommentsAdapter(comments) { position ->
                    val selectedComment = comments[position]
                    Log.d("MessageThreadFragment", "position: $position")
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }

//        binding.buttonNewComment.setOnClickListener {
//            loginSignupViewModel.userLiveData.observe(viewLifecycleOwner) {firebaseUser ->
//                if (firebaseUser != null) {
//                    Log.d("MessageStreamFragment", "firebaseUser != null")
//                    val content = binding.newCommentTextEdit.text.toString().trim()
//                    val user = firebaseUser.email.toString().trim()
//                    val newComment = Comment(content, user)
//                    messagesViewModel.postComment(newComment)
//                    Log.d("MessageStreamFragment", "post $newComment")
//                    messagesViewModel.loadMessages()
//                    binding.newCommentTextEdit.text.clear()
//                } else {
//                    val log = "Failed to add new message"
//                    Log.d("MessageStreamFragment",log)
//                }
//            }
//        }
        binding.swipeRefresh.setOnRefreshListener{
            messagesViewModel.loadMessages()
            Log.d("MessageThreadFragment","swipe refresh: Comments loaded")
            binding.swipeRefresh.isRefreshing = false

        }


    }
}