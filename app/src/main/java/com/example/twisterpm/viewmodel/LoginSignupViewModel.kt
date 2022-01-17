package com.example.twisterpm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.twisterpm.model.LoginSignupRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginSignupViewModel : ViewModel() {
    private val loginSignupRepository = LoginSignupRepository()
    private var firebaseAuth = FirebaseAuth.getInstance()
    val userLiveData : MutableLiveData<FirebaseUser?> = loginSignupRepository.userLiveData
    val errorLiveData = loginSignupRepository.errorLiveData

    fun login(email: String, password: String) {
        loginSignupRepository.login(email, password)
    }

    fun signup(email: String, password: String) {
        loginSignupRepository.signup(email, password)
    }

    fun signOut() {
        loginSignupRepository.signOut()
    }

}