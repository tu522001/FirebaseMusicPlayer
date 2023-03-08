package com.example.firebasemusicplayer.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
    private val handler = Handler(Looper.getMainLooper())
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


        binding.btnConfirm.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            clickSignUp(email, password)
        }

        return binding.root
    }

    private fun clickSignUp(email: String, password: String) {
        var auth = FirebaseAuth.getInstance()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Xác thực thành công
                        Toast.makeText(
                            requireContext(),
                            "Đăng nhập thành công.",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Tiếp tục với các bước tiếp theo
                    } else {
                        // Xác thực thất bại
                        if (email.equals("@") && email.equals(".")){
                            Toast.makeText(requireContext(), "Email hãy thêm @", Toast.LENGTH_SHORT).show()
                        }else if (password.length < 6){
                            Toast.makeText(requireContext(), "Password phải trên 6 ký tự", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(
                                requireContext(),
                                "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin đăng nhập.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        } else {
            Toast.makeText(
                requireContext(),
                "Vui lòng nhập đầy đủ thông tin đăng nhập.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}