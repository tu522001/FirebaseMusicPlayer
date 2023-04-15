package com.example.firebasemusicplayer.view.user.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {

    private lateinit var binding : FragmentHelpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_help,container,false)

        binding.imgBtnBack.setOnClickListener{
            findNavController().navigate(R.id.action_helpFragment_to_facebookFragment3)
        }
        return binding.root
    }


}