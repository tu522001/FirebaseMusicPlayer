package com.example.firebasemusicplayer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.firebasemusicplayer.model.entity.Music
import com.example.firebasemusicplayer.repository.SongRepository

class AddSongViewModel : ViewModel() {

    private val repository = SongRepository()

    fun saveSong(id: Int, imageURL: String, singerName: String, songName: String, songURL: String) {
        val music = Music(id, imageURL, singerName, songName, songURL)
        repository.saveSong(music)
    }

}