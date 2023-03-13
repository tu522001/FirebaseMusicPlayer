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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.firebasemusicplayer.MainActivity
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentSignInBinding
import com.example.firebasemusicplayer.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        binding.loginbtn.setOnClickListener {
            val signInData = User(binding.emails.text.toString(), binding.passwords.text.toString())
            viewModel.authenticateUser(signInData)
        }

        viewModel.isAuthenticated.observe(viewLifecycleOwner, Observer { success ->
            if (success) {
                Toast.makeText(
                    requireContext(), "User logged in successfully",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
            } else {
                Toast.makeText(
                    requireContext(), "Log in Error: ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        return binding.root
    }
}