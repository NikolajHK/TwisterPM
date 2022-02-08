package com.example.twisterpm.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
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
    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }

    private var _binding: FragmentLoginsignupBinding? = null
    private val binding get() = _binding!!
    private val loginSignupViewModel : LoginSignupViewModel by activityViewModels()
    private val messagesViewModel : MessagesViewModel by activityViewModels()
    private lateinit var savedStateHandle: SavedStateHandle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginsignupBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle.set(LOGIN_SUCCESSFUL,false)

        messagesViewModel.loadMessages()

        binding.buttonSignup.setOnClickListener {
            val email = binding.editLoginUsername.text.toString().trim()
            val password = binding.editLoginPassword.text.toString().trim()
            loginSignupViewModel.signup(email, password)
            Log.d("LoginSignupFragment", "signed up with email: $email and password: $password")
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.editLoginUsername.text.toString().trim()
            val password = binding.editLoginPassword.text.toString().trim()
            login(email, password)
            Log.d("LoginSignupFragment", "logged in with email: $email and password: $password")
        }

        loginSignupViewModel.userLiveData.observe(viewLifecycleOwner, Observer {firebaseUser ->
            if (firebaseUser != null) {
                savedStateHandle.set(LOGIN_SUCCESSFUL,true)
                findNavController().popBackStack()
            } else {
                Log.d("LoginSignupFragment", "firebaseUser is still null")
            }
        })


        loginSignupViewModel.errorLiveData.observe(viewLifecycleOwner) {errorMessage ->
            if (errorMessage != null) {
                binding.errorMessageView.text = errorMessage
            }
        }
    }

    fun login(email: String, password: String) {
        loginSignupViewModel.login(email, password)
        loginSignupViewModel.userLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                savedStateHandle.set(LOGIN_SUCCESSFUL,true)
                findNavController().popBackStack()
            } else {
                Log.d("LoginSignupFragment", "firebaseUser is still null")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}