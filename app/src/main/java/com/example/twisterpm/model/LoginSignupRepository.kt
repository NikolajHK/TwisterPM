package com.example.twisterpm.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.MutableLiveData


class LoginSignupRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val userLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()


    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    val message = "Failed to login: " + task.exception!!.message
                    errorLiveData.value = message
                    Log.d("LoginSignupRepository",message)
                }
            }
    }

    fun signup(email:String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword((email), password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    val message = "Signup Failed: " + task.exception!!.message
                    errorLiveData.value = message
                    Log.d("LoginSignupRepository",message)
                }
            }
    }

    fun signOut() {
        TODO()
        firebaseAuth.signOut()
        if (firebaseAuth.currentUser == null) {
            userLiveData.postValue(firebaseAuth.currentUser)
        } else {
            val message = "Sign Out Failed"
            errorLiveData.value = message
            Log.d("LoginSignupRepository",message)
        }
    }

//    init {
//        if (firebaseAuth.currentUser !=null) {
//            userLiveData.postValue(firebaseAuth.currentUser)
//            loggedOutLiveData.postValue(false)
//        }
//    }

}