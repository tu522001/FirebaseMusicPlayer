package com.example.firebasemusicplayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel : ViewModel() {

    private var _signUpSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val signUpSuccess: LiveData<Boolean>
        get() = _signUpSuccess

    fun signUp(name: String, email: String, phone: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty()) {
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _signUpSuccess.postValue(true)
                    } else {
                        _signUpSuccess.postValue(false)
                    }
                }
        }
    }
}