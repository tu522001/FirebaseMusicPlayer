package com.example.firebasemusicplayer.login

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel : ViewModel() {
//  private lateinit var mAuth: FirebaseAuth
//
//  fun init(){
//      mAuth = FirebaseAuth.getInstance()
//  }
//
//    private fun clickSignUp(email: String, password: String) : LiveData<Boolean> {
//        var signUpResult = MutableLiveData<Boolean>()
//
//        if (email.isNotEmpty() && password.isNotEmpty()) {
//            mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        // Xác thực thành công
//                        Toast.makeText(
//                            requireContext(),
//                            "Đăng nhập thành công.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        // Tiếp tục với các bước tiếp theo
//                    } else {
//                        // Xác thực thất bại
//                        if (email.equals("@") && email.equals(".")){
//                            Toast.makeText(requireContext(), "Email hãy thêm @", Toast.LENGTH_SHORT).show()
//                        }else if (password.length < 6){
//                            Toast.makeText(requireContext(), "Password phải trên 6 ký tự", Toast.LENGTH_SHORT).show()
//                        }else{
//                            Toast.makeText(
//                                requireContext(),
//                                "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin đăng nhập.",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                }
//        } else {
//            Toast.makeText(
//                requireContext(),
//                "Vui lòng nhập đầy đủ thông tin đăng nhập.",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//        return signUpResult
//    }






//private val _message = MutableLiveData<String>()
//    val message: LiveData<String>
//        get() = _message
//
//    fun signUp(email: String, password: String) {
//        if (email.isNotEmpty() && password.isNotEmpty()) {
//            val auth = FirebaseAuth.getInstance()
//            auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        _message.value = "Đăng nhập thành công."
//                    } else {
//                        if (email.equals("@") && email.equals(".")){
//                            _message.value = "Email hãy thêm @"
//                        }else if (password.length < 6){
//                            _message.value = "Password phải trên 6 ký tự"
//                        }else{
//                            _message.value = "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin đăng nhập."
//                        }
//                    }
//                }
//        } else {
//            _message.value = "Vui lòng nhập đầy đủ thông tin đăng nhập."
//        }
//    }


    private var _signUpSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val signUpSuccess: LiveData<Boolean>
        get() = _signUpSuccess

    fun signUp(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
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