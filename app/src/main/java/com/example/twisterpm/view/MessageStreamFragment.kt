package com.example.twisterpm.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
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

        loginSignupViewModel.userLiveData.observe(viewLifecycleOwner) {user ->
            if (user != null) {
                binding.LoggedInUserView.text= "Logged in as " + user.email
            }
        }

        //Messages
        messagesViewModel.loadMessages()
        Log.d("MessageStreamFragment","loaded messages")

        messagesViewModel.messagesLiveData.observe(viewLifecycleOwner) { messages ->
            if (messages != null) {
                val adapter = MessagesAdapter(messages) { position ->
                    val selectedMessage = messages[position]
                    val action = MessageStreamFragmentDirections.actionMessageStreamFragmentToMessageThreadFragment(position)
                    findNavController().navigate(action)
                    Log.d("MessageStreamFragment", "position: $position")
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }

        binding.buttonNewMessage.setOnClickListener {
            loginSignupViewModel.userLiveData.observe(viewLifecycleOwner) {firebaseUser ->
                if (firebaseUser != null) {
                    Log.d("MessageStreamFragment", "firebaseUser != null")
                    val content = binding.newMessageTextEdit.text.toString().trim()
                    val user = firebaseUser.email.toString().trim()
                    val newMessage = Message(content, user)
                    messagesViewModel.postMessage(newMessage)
                    Log.d("MessageStreamFragment", "post $newMessage")
                    messagesViewModel.loadMessages()
                    binding.newMessageTextEdit.text.clear()
                } else {
                    val message = "Failed to add new message"
                    Log.d("MessageStreamFragment",message)
                }
            }
        }


        messagesViewModel.messageErrorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.ErrorTextViewMessageStream.text = errorMessage

        }

        //Logout
        binding.ButtonLogOut.setOnClickListener {
            loginSignupViewModel.signOut()
            Log.d("MessageStreamFragment", "Sign out successful")
            loginSignupViewModel.userLiveData.observe(viewLifecycleOwner) { firebaseUser ->
                if (firebaseUser == null){
                    Log.d("MessageStreamFragment","firebaseUser now null")
                    val action = MessageStreamFragmentDirections.actionMessageStreamFragmentToLoginSignupFragment()
                    findNavController().navigate(action)
                    Log.d("MessageStreamFragment","Attempting to move: $action" )
                }
            }
        }

        binding.swipeRefresh.setOnRefreshListener{
            messagesViewModel.loadMessages()
            Log.d("MessageStreamFragment","swipe refresh: messages loaded")
            binding.swipeRefresh.isRefreshing = false

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}