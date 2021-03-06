package com.example.twisterpm.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twisterpm.databinding.FragmentMessagestreamBinding
import com.example.twisterpm.viewmodel.MessagesViewModel
import com.example.twisterpm.model.Message
import com.example.twisterpm.model.MessagesAdapter
import com.example.twisterpm.viewmodel.LoginSignupViewModel

class MessageStreamFragment : Fragment() {

    private var _binding: FragmentMessagestreamBinding? = null
    private val binding get() = _binding!!
    private val messagesViewModel : MessagesViewModel by activityViewModels()
    private val loginSignupViewModel : LoginSignupViewModel by activityViewModels()
//    private val args: MessageStreamFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentBackStackEntry = findNavController().currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(LoginSignupFragment.LOGIN_SUCCESSFUL)
            .observe(currentBackStackEntry, Observer { success ->
                if (!success) {
                    val startDestination = findNavController().graph.startDestination
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(startDestination, true)
                        .build()
                    findNavController().navigate(startDestination, null, navOptions)
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)
        _binding = FragmentMessagestreamBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginSignupViewModel.userLiveData.observe(viewLifecycleOwner) {firebaseUser ->
            if (firebaseUser != null) {
                binding.LoggedInUserView.text= "Logged in as " + firebaseUser.email
            } else {
                Log.d("MessageStreamFragment","firebaseUser is null")
                val action = MessageStreamFragmentDirections.actionMessageStreamFragmentToLoginSignupFragment()
                findNavController().navigate(action)
                Log.d("MessageStreamFragment","Attempting to move: $action" )
            }
        }

        //Messages
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
//            loginSignupViewModel.userLiveData.observe(viewLifecycleOwner) { firebaseUser ->
//                if (firebaseUser == null){
//                    Log.d("MessageStreamFragment","firebaseUser now null")
//                    val action = MessageStreamFragmentDirections.actionMessageStreamFragmentToLoginSignupFragment()
//                    findNavController().navigate(action)
//                    Log.d("MessageStreamFragment","Attempting to move: $action" )
//                }
//            }
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