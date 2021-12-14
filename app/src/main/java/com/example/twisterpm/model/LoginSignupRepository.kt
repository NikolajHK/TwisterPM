package com.example.twisterpm.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.MutableLiveData


class LoginSignupRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val userLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val loggedOutLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()


    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    errorLiveData.value =
                        "Failed to login: " + task.exception!!.message
                }
            }
    }

    fun signup(email:String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword((email), password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    errorLiveData.value =
                        "Signup Failed: " + task.exception!!.message
                }
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
        firebaseAuth.currentUser;
        loggedOutLiveData.postValue(true)
    }

//    init {
//        if (firebaseAuth.currentUser !=null) {
//            userLiveData.postValue(firebaseAuth.currentUser)
//            loggedOutLiveData.postValue(false)
//        }
//    }

}