package com.example.firebasemusicplayer.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebasemusicplayer.MainActivity
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentSignInBinding>(
            inflater,
            R.layout.fragment_sign_in,
            container,
            false
        )
        clickSignUp()

        mAuth = FirebaseAuth.getInstance()
        binding.loginbtn.setOnClickListener {
            loginUser(emails.text.toString(), passwords.text.toString());
        }
        return binding.root
    }

    private fun loginUser(email: String, password: String) {
        if (TextUtils.isEmpty(email)) {
            emails.setError("Email cannot be empty")
            emails.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            passwords.setError("Password cannot be empty")
            passwords.requestFocus()
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(), "User logged in successfully",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                } else {
                    Toast.makeText(
                        requireContext(), "Log in Error: ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun clickSignUp() {
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

}