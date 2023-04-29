package com.example.firebasemusicplayer.view.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

//class AddSongViewModel : ViewModel() {
//    private var _addSongSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
//    val addSongSuccess: LiveData<Boolean>
//        get() = _addSongSuccess
//
//    fun addSong(id: String, imageURL: String, singerName: String, songName: String, songURL: String) {
//        if (id.isNotEmpty() && imageURL.isNotEmpty() && singerName.isNotEmpty() && songName.isNotEmpty() && songURL.isNotEmpty()) {
//
//            val auth = FirebaseAuth.getInstance()
//            auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        _addSongSuccess.postValue(true)
//                    } else {
//                        _addSongSuccess.postValue(false)
//                    }
//                }
//        }
//    }
//}