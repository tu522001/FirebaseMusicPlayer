package com.example.firebasemusicplayer.repository

import com.example.firebasemusicplayer.BASE_URL
import com.example.firebasemusicplayer.model.entity.Music
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SongRepository {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReferenceFromUrl(BASE_URL)

    fun saveSong(music : Music) {
        databaseReference.child("Song").child(music.id.toString()).setValue(music)
    }
}