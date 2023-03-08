package com.example.firebasemusicplayer.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentScreenBinding
import com.example.firebasemusicplayer.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentSignUpBinding>(
            inflater,
            R.layout.fragment_sign_up,
            container,
            false
        )


        binding.button2.setOnClickListener {
            clickButton()
        }


        return binding.root
    }

    private fun clickButton() {
        val email = edt_email.text.toString()
        val password = edt_password.text.toString()
        var auth = FirebaseAuth.getInstance()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Xác thực thành công
//                        val user = auth.currentUser
                        Toast.makeText(requireContext(), "Đăng nhập thành công.", Toast.LENGTH_SHORT).show()
                        // Tiếp tục với các bước tiếp theo
                    } else {
                        // Xác thực thất bại
                        Toast.makeText(requireContext(), "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin đăng nhập.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(requireContext() , "Vui lòng nhập đầy đủ thông tin đăng nhập.", Toast.LENGTH_SHORT).show()
        }
    }
}