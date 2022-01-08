package com.example.twisterpm.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val email: FirebaseUser?,
    val password: FirebaseUser?
) : Parcelable
