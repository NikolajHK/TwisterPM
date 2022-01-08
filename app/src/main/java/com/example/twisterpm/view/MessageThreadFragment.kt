package com.example.twisterpm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.twisterpm.R
import com.example.twisterpm.databinding.FragmentMessagestreamBinding
import com.example.twisterpm.databinding.FragmentMessagethreadBinding
import com.example.twisterpm.model.Comment
import com.example.twisterpm.model.Message
import com.example.twisterpm.viewmodel.MessagesViewModel

class MessageThreadFragment : Fragment() {

    private var _binding: FragmentMessagethreadBinding? = null
    private val binding get() = _binding!!
    private val messagesViewModel : MessagesViewModel by activityViewModels()
    private val args: MessageThreadFragmentArgs by navArgs()
    private lateinit var message: Message
    private lateinit var comment: Comment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        message = args.message
        _binding = FragmentMessagethreadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO()
//        val bundle = requireArguments()
//        val messageThreadFragmentArgs
//        : MessageThreadFragmentArgs = MessageThreadFragmentArgs.fromBundle(bundle)
//        val position = messageThreadFragmentArgs.position
//        val message = messagesViewModel[position]

        binding.userTextview.text = message.user
        binding.contentTextview.text = message.content


    }
}