package com.example.firebasemusicplayer.view.fragment.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.firebasemusicplayer.model.entity.User
import com.example.firebasemusicplayer.viewmodel.SignInViewModel
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel
    private lateinit var bundle : Bundle
    private lateinit var callbackManager : CallbackManager
    private lateinit var signInData : User
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

            bundle = Bundle().apply {
                putString("Key_email", "example@email.com")
            }
            val signInFragment = SignInFragment()
            Log.d("RRR","signInFragment : "+signInFragment)
            signInFragment.arguments = bundle

            Log.d("RRR","signInFragment.arguments : "+signInFragment.arguments)
            Log.d("RRR","bundle : "+bundle)

            if (success) {
                if (binding.emails.text.toString() == "admin123@gmail.com" && (binding.passwords.text.toString() == "admin123")){
                    findNavController().navigate(R.id.action_signInFragment_to_adminFragment)
                }else{
                    findNavController().navigate(R.id.action_signInFragment_to_facebookFragment3,bundle)
                    Log.d("AAAS","signInDatassss : "+bundle)
                }
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


        callbackManager = CallbackManager.Factory.create()

        var accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null && !accessToken.isExpired){
            accessToken.token.toString()
            findNavController().navigate(R.id.action_signInFragment_to_facebookFragment3)
        }

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