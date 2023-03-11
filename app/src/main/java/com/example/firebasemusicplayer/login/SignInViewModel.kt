package com.example.firebasemusicplayer.login

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel : ViewModel() {
//    private lateinit var mAuth: FirebaseAuth
//
//    fun init() {
//        mAuth = FirebaseAuth.getInstance()
//    }
//
//    fun loginUser(email: String, password: String): LiveData<Boolean> {
//        val loginResult = MutableLiveData<Boolean>()
//        if (TextUtils.isEmpty(email)) {
//            loginResult.value = false
//        } else if (TextUtils.isEmpty(password)) {
//            loginResult.value = false
//        } else {
//            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    loginResult.value = true
//                } else {
//                    loginResult.value = false
//                }
//            }
//        }
//        return loginResult
//    }

    private val mAuth = FirebaseAuth.getInstance()

    private val _signInSuccess = MutableLiveData<Boolean>()
    val signInSuccess: LiveData<Boolean>
        get() = _signInSuccess

    fun signIn(email: String, password: String) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            _signInSuccess.value = false
            return
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            _signInSuccess.value = task.isSuccessful
        }
    }
}