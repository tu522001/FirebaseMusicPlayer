package com.example.firebasemusicplayer.repository

import com.example.firebasemusicplayer.model.entity.Music
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SongRepository {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-musicplayer-e2d98-default-rtdb.firebaseio.com/")

    fun saveSong(music : Music) {
        databaseReference.child("Song").child(music.id.toString()).setValue(music)
    }
}