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


class SecurityFragment : Fragment() {

    private lateinit var binding : FragmentSecurityBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_security,container,false)

        binding.imgBtnBack.setOnClickListener{
            findNavController().navigate(R.id.action_securityFragment_to_facebookFragment3)
        }
        return binding.root
    }

}