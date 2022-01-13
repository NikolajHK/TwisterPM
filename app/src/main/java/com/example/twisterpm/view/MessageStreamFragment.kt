package com.example.twisterpm.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twisterpm.databinding.FragmentMessagestreamBinding
import com.example.twisterpm.viewmodel.MessagesViewModel
import com.example.twisterpm.model.Message
import com.example.twisterpm.viewmodel.LoginSignupViewModel

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

        loginSignupViewModel.userLiveData.observe(viewLifecycleOwner) {user ->
            if (user != null) {
                binding.LoggedInUserView.text= "Logged in as " + user.email
            }
        }

        //Messages
        val selectedMessage = messagesViewModel.selectedMessage

        messagesViewModel.loadMessages()
        Log.d("MessageStreamFragment","loaded messages")

        fun reloadMessages() {
            messagesViewModel.loadMessages()
            Log.d("MessageThreadFragment","reloaded messages")
        }

        messagesViewModel.messagesLiveData.observe(viewLifecycleOwner) { messages ->
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (messages == null) View.GONE else View.VISIBLE
            if (messages != null) {
                val adapter = MessagesAdapter(messages) { position ->
                    messagesViewModel.selectedMessage = messages[position]
                    val action = MessageStreamFragmentDirections.actionMessageStreamFragmentToMessageThreadFragment(position)
                    findNavController().navigate(action)
                    Log.d("MessageStreamFragment", "position: $position")
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }
        binding.buttonDeleteMessage.setOnClickListener {
            loginSignupViewModel.userLiveData.observe(viewLifecycleOwner) { firebaseUser ->
                if (firebaseUser?.email == selectedMessage.user) {
                    Log.d(
                        "MessageThreadFragment",
                        "Logged in Firebase User: ${firebaseUser.email} is similar to message poster: ${selectedMessage.user}"
                    )
                    messagesViewModel.deleteMessage(selectedMessage.id)
                    Log.d("MessageThreadFragment","deleted $selectedMessage")
                    reloadMessages()
                } else {
                    val log = "Failed to delete comment"
                    Log.d("MessageStreamFragment", log)
                }
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
                    reloadMessages()
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
            Log.d("MessageStreamFragment","swipe refresh: messages reloaded")
            binding.swipeRefresh.isRefreshing = false

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}