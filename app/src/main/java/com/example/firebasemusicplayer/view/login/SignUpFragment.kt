package com.example.firebasemusicplayer.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentSignUpBinding
import com.example.firebasemusicplayer.viewmodel.SignUpViewModel


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel

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

        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        binding.btnConfirm.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            signUpViewModel.signUp(email, password)
        }

        binding.imgBtnLogout.setOnClickListener{
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        signUpViewModel.signUpSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                // Xác thực thành công
                Toast.makeText(
                    requireContext(),
                    "Đăng ký tài khoản thành công.",
                    Toast.LENGTH_SHORT
                ).show()
                // Tiếp tục với các bước tiếp theo
            } else {
                Toast.makeText(
                    requireContext(),
                    "Đăng ký thất bại.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        return binding.root
    }
}

