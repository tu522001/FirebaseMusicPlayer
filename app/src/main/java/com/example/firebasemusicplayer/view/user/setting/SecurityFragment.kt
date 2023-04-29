package com.example.firebasemusicplayer.view.user.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentSecurityBinding
import com.google.firebase.auth.FirebaseAuth


class SecurityFragment : Fragment() {

    private lateinit var binding : FragmentSecurityBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_security,container,false)



        // gọi giá trị email của firebase ra thông qua phương thức dưới đây
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email

        println(userEmail)

        if (userEmail == "admin123@gmail.com"){
            binding.imgBtnBack.setOnClickListener {
                findNavController().navigate(R.id.action_securityFragment_to_adminFragment)
            }
        }else{
            binding.imgBtnBack.setOnClickListener{
                findNavController().navigate(R.id.action_securityFragment_to_facebookFragment3)
            }
        }
        return binding.root
    }

}