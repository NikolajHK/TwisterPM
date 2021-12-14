package com.example.twisterpm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.twisterpm.model.LoginSignupRepository

class LoginSignupViewModel : ViewModel() {
    private val repository = LoginSignupRepository()
    val userLiveData = repository.userLiveData
    val errorLiveData = repository.errorLiveData

    fun login(email: String, password: String) {
        repository.login(email, password)
    }

    fun signup(email: String, password: String) {
        repository.signup(email, password)
    }

}