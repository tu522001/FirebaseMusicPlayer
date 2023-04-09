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

//import com.google.firebase.ktx.Firebase
//import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {
//    private lateinit var binding: FragmentSignUpBinding
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding = DataBindingUtil.inflate<FragmentSignUpBinding>(
//            inflater,
//            R.layout.fragment_sign_up,
//            container,
//            false
//        )
//
//
//        binding.btnConfirm.setOnClickListener {
//            val email = binding.edtEmail.text.toString()
//            val password = binding.edtPassword.text.toString()
//            clickSignUp(email, password)
//        }
//
//        return binding.root
//    }
//
//    private fun clickSignUp(email: String, password: String) {
//        var auth = FirebaseAuth.getInstance()
//        if (email.isNotEmpty() && password.isNotEmpty()) {
//            auth.createUserWithEmailAndPassword(email, password)
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
//    }



//    private lateinit var binding: FragmentSignUpBinding
//    private val viewModel: SignUpViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = DataBindingUtil.inflate(
//            inflater,
//            R.layout.fragment_sign_up,
//            container,
//            false
//        )
//
//        binding.btnConfirm.setOnClickListener {
//            val email = binding.edtEmail.text.toString()
//            val password = binding.edtPassword.text.toString()
//            viewModel.signUp(email, password)
//        }
//
//        viewModel.message.observe(viewLifecycleOwner, {
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//        })
//
//        return binding.root
//    }



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

