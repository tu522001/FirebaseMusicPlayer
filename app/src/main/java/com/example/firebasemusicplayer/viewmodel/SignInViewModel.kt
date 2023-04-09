package com.example.firebasemusicplayer.viewmodel

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasemusicplayer.model.User
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel : ViewModel() {

    private val mAuth = FirebaseAuth.getInstance()

    private val _isAuthenticated = MutableLiveData<Boolean>()
    val isAuthenticated: LiveData<Boolean>
        get() = _isAuthenticated

    fun authenticateUser(user: User) {
        if (TextUtils.isEmpty(user.email) || TextUtils.isEmpty(user.password)) {
            _isAuthenticated.value = false
            return
        }
        mAuth.signInWithEmailAndPassword(user.email,user.password).addOnCompleteListener { task ->
            _isAuthenticated.value = task.isSuccessful
        }
    }

}