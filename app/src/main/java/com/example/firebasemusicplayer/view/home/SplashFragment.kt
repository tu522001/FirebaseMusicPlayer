package com.example.firebasemusicplayer.view.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentSplashBinding
import com.example.firebasemusicplayer.view.home.SplashFragment

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashFragment : Fragment() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentSplashBinding>(
            inflater,
            R.layout.fragment_splash,
            container,
            false
        )

        handler.postDelayed({
            navigateToSignIn()
        }, 2000)

        return binding.root
    }

    private fun navigateToSignIn() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null){
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
        }else{
//            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
        }
    }
}