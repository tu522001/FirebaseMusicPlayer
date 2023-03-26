package com.example.firebasemusicplayer.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentSignInBinding
import com.example.firebasemusicplayer.model.User
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.util.*


//import kotlinx.android.synthetic.main.fragment_sign_in.*
//import kotlinx.android.synthetic.main.fragment_sign_in.view.*


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel

    private lateinit var callbackManager : CallbackManager

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
//                Toast.makeText(
//                    requireContext(), "User logged in successfully",
//                    Toast.LENGTH_SHORT
//                ).show()
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


        callbackManager = CallbackManager.Factory.create();

        var accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null && !accessToken.isExpired){
            findNavController().navigate(R.id.action_signInFragment_to_facebookFragment3)
        }

//        LoginManager.getInstance().registerCallback(callbackManager,
//            object : FacebookCallback<LoginResult?> {
//                override fun onSuccess(result: LoginResult?) {
//                    // App code
//                    findNavController().navigate(R.id.action_signInFragment_to_facebookFragment3)
//                }
//
//                override fun onCancel() {
//                    // App code
//                }
//
//                override fun onError(exception: FacebookException) {
//                    // App code
//                }
//            })

        LoginManager.getInstance().registerCallback(callbackManager,object :FacebookCallback<LoginResult>{

            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {

            }

            override fun onSuccess(result: LoginResult) {
                findNavController().navigate(R.id.action_signInFragment_to_facebookFragment3)
            }

        })

        binding.imgFacebook.setOnClickListener{
            LoginManager.getInstance().logInWithReadPermissions(this, mutableListOf("public_profile"));
        }

        return binding.root
    }


     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}