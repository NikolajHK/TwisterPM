package com.example.twisterpm.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.twisterpm.R
import com.example.twisterpm.databinding.FragmentMessagestreamBinding
import com.example.twisterpm.viewmodel.MessagesViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MessageStreamFragment : Fragment() {

    private var _binding: FragmentMessagestreamBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val messagesViewModel : MessagesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMessagestreamBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messagesViewModel.userLiveData.observe(viewLifecycleOwner){firebaseUser ->
            if (firebaseUser != null) {
                binding.LoggedInUserView.text= firebaseUser.email
            }
        }

        binding.ButtonLogOut.setOnClickListener {
            messagesViewModel.signOut()
        }

        messagesViewModel.userLiveData.observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser == null){
                findNavController().navigate(R.id.action_MessageStreamFragment_to_LoginSignupFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}