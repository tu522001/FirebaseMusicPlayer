package com.example.firebasemusicplayer.view.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentAddSongBinding

class AddSongFragment : Fragment() {


    private lateinit var binding : FragmentAddSongBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_song,container,false)
        return binding.root
    }

}