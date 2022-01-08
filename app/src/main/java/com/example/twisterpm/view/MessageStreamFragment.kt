package com.example.twisterpm.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twisterpm.R
import com.example.twisterpm.databinding.FragmentMessagestreamBinding
import com.example.twisterpm.model.MessagesAdapter
import com.example.twisterpm.viewmodel.MessagesViewModel
import com.example.twisterpm.model.Message
import com.example.twisterpm.viewmodel.LoginSignupViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MessageStreamFragment : Fragment() {

    private var _binding: FragmentMessagestreamBinding? = null
    private val binding get() = _binding!!
    private val messagesViewModel : MessagesViewModel by activityViewModels()
    private val loginSignupViewModel : LoginSignupViewModel by activityViewModels()
//    private val args: MessageStreamFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMessagestreamBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val bundle = requireArguments()
//        val messageStreamFragmentArgs
//        : MessageStreamFragmentArgs = MessageStreamFragmentArgs.fromBundle(bundle)
//        val user = args.user
//        if (user != null) {
//            messagesViewModel.userLiveData.postValue(user.email)
//        }

        messagesViewModel.userLiveData.observe(viewLifecycleOwner) {user ->
            if (user != null) {
                binding.LoggedInUserView.text= user.email
            }
        }


        //Auth
        messagesViewModel.userLiveData.observe(viewLifecycleOwner) {firebaseUser ->
            if (firebaseUser != null) {
                binding.LoggedInUserView.text= firebaseUser.email
                val user = firebaseUser.email
            }
        }

        //Messages
        messagesViewModel.loadMessages()

        messagesViewModel.messagesLiveData.observe(viewLifecycleOwner) { messages ->
            if (messages != null) {
                val adapter = MessagesAdapter(messages) { position ->
                    val position = position
                    val message = message
                    val action = MessageStreamFragmentDirections.actionMessageStreamFragmentToMessageThreadFragment(position, message)
                    findNavController().navigate(action)
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }

        binding.buttonNewMessage.setOnClickListener {
            messagesViewModel.userLiveData.observe(viewLifecycleOwner) {firebaseUser ->
                if (firebaseUser != null) {
                    val user = firebaseUser.email.toString().trim()
                    val content = binding.newMessageTextEdit.text.toString().trim()
                    val newMessage = Message(user, content)
                    Log.d("MessageStreamFragment", "add $newMessage")
                    messagesViewModel.postMessage(newMessage)
                    messagesViewModel.loadMessages()
                    binding.newMessageTextEdit.text.clear()
                } else {
                    val message = "Failed to add new message"
                    Log.d("MessageStreamFragment",message)
                }
            }
        }


        messagesViewModel.messageErrorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.ErrorTextViewmessageStream.text = errorMessage

        }

        //Logout
        binding.ButtonLogOut.setOnClickListener {
            messagesViewModel.signOut()
            messagesViewModel.userLiveData.observe(viewLifecycleOwner) { firebaseUser ->
                if (firebaseUser == null){
                findNavController().navigate(R.id.action_MessageStreamFragment_to_LoginSignupFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}