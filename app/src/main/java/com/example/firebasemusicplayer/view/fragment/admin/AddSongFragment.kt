package com.example.firebasemusicplayer.view.fragment.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentAddSongBinding
import com.example.firebasemusicplayer.viewmodel.AddSongViewModel
import com.google.firebase.database.*


class AddSongFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding : FragmentAddSongBinding
    private lateinit var viewModel: AddSongViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_song, container, false)
        viewModel = ViewModelProvider(this)[AddSongViewModel::class.java]

        binding.btnConfirm.setOnClickListener {
            val id = binding.editTextID.text.toString().toIntOrNull()
            val imageURL = binding.editTextImageURL.text.toString()
            val singerName = binding.editTextSingerName.text.toString()
            val songName = binding.editTextSongName.text.toString()
            val songURL = binding.editTextSongURL.text.toString()

            if (id == null || imageURL.isEmpty() || singerName.isEmpty() || songName.isEmpty() || songURL.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.saveSong(id, imageURL, singerName, songName, songURL)
            }
        }

        return binding.root
    }

}
