package com.example.twisterpm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.twisterpm.model.LoginSignupRepository

class LoginSignupViewModel : ViewModel() {
    private val loginSignupRepository = LoginSignupRepository()
    val userLiveData = loginSignupRepository.userLiveData
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