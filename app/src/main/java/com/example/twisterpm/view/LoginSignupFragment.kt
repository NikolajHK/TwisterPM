package com.example.twisterpm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.twisterpm.R
import com.example.twisterpm.databinding.FragmentLoginsignupBinding
import com.example.twisterpm.model.MessagesRepository
import com.example.twisterpm.model.User
import com.example.twisterpm.viewmodel.LoginSignupViewModel
import com.example.twisterpm.viewmodel.MessagesViewModel
import com.google.firebase.auth.FirebaseUser


class LoginSignupFragment : Fragment() {

    private var _binding: FragmentLoginsignupBinding? = null
    private val binding get() = _binding!!
    private val loginSignupViewModel : LoginSignupViewModel by activityViewModels()
    private val messagesViewModel : MessagesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginsignupBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = loginSignupViewModel.userLiveData

        messagesViewModel.loadMessages()

        binding.buttonSignup.setOnClickListener {
            val email = binding.editLoginUsername.text.toString().trim()
            val password = binding.editLoginPassword.text.toString().trim()
            loginSignupViewModel.signup(email, password)
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.editLoginUsername.text.toString().trim()
            val password = binding.editLoginPassword.text.toString().trim()
            loginSignupViewModel.login(email, password)
        }
        loginSignupViewModel.userLiveData.observe(viewLifecycleOwner){firebaseUser ->
            if (firebaseUser != null) {
//                val user = currentUser
                val action= LoginSignupFragmentDirections.actionLoginSignupFragmentToMessageStreamFragment()
                findNavController().navigate(action)
            }
        }

        loginSignupViewModel.errorLiveData.observe(viewLifecycleOwner) {errorMessage ->
            if (errorMessage != null) {
                binding.errorMessageView.text = errorMessage
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}