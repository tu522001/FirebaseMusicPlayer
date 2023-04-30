package com.example.firebasemusicplayer.view.admin

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
        // Inflate the layout for this fragment

//        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_song,container,false)
//
//        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-musicplayer-e2d98-default-rtdb.firebaseio.com/")
//        binding.btnConfirm.setOnClickListener {
//
//            // get data from EditText into String variables
//            val id = binding.editTextID.text.toString().toInt()
//            val imageURL = binding.editTextImageURL.text.toString()
//            val singerName = binding.editTextSingerName.text.toString()
//            val songName = binding.editTextSongName.text.toString()
//            val songURL = binding.editTextSongURL.text.toString()
//
//            //check if user fill all the fields before sending data to firebase
//            if (id == null || imageURL.isEmpty() || singerName.isEmpty() || songName.isEmpty() || songURL.isEmpty()){
//                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
//            }
//
//            else{
//
//                databaseReference.child("Song").addListenerForSingleValueEvent( object :
//                    ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if (snapshot.hasChild(id.toString())){
//                            Toast.makeText(requireContext(), "ID đã được đăng ký", Toast.LENGTH_SHORT).show()
//                        }else{
//                            // sending data to  firebase Realtime Database
//                            // we are using phone number as unique identity of every user
//                            // so all the other details of user comes under phone number
//
//                            databaseReference.child("Song").child(id.toString()).child("id").setValue(id)
//                            databaseReference.child("Song").child(id.toString()).child("imageURL").setValue(imageURL)
//                            databaseReference.child("Song").child(id.toString()).child("singerName").setValue(singerName)
//                            databaseReference.child("Song").child(id.toString()).child("songName").setValue(songName)
//                            databaseReference.child("Song").child(id.toString()).child("songURL").setValue(songURL)
//
//                        }
//                    }
//
//                    override fun onCancelled(p0: DatabaseError) {
//                        TODO("Not yet implemented")
//                    }
//
//                })
//
//            }
//
//        }
//
//        return binding.root

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
