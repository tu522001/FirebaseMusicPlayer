package com.example.firebasemusicplayer.utils.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentTermsOfServiceBinding
import com.google.firebase.auth.FirebaseAuth


class TermsOfServiceFragment : Fragment() {

    private lateinit var binding : FragmentTermsOfServiceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_terms_of_service, container, false)



        // gọi giá trị email của firebase ra thông qua phương thức dưới đây
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email

        println(userEmail)

        if (userEmail == "admin123@gmail.com"){
            binding.imgBtnBack.setOnClickListener {
                findNavController().navigate(R.id.action_termsOfServiceFragment_to_adminFragment)
            }
        }else{
            binding.imgBtnBack.setOnClickListener {
                findNavController().navigate(R.id.action_termsOfServiceFragment_to_facebookFragment3)
            }
        }
        return binding.root
    }

}