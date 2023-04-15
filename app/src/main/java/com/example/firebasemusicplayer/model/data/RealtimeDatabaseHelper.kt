package com.example.firebasemusicplayer.model.data

import android.util.Log
import com.example.firebasemusicplayer.model.entity.Music
import com.example.firebasemusicplayer.model.entity.Photo
import com.example.firebasemusicplayer.model.entity.Singer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RealtimeDatabaseHelper {
    private val database = FirebaseDatabase.getInstance()
    private val database1 = FirebaseDatabase.getInstance()
    private val databaseListMusic = FirebaseDatabase.getInstance()

    private val myRef = database.getReference("Song")
    private val myRef1 = database1.getReference("Singer")
    private val myRef2 = databaseListMusic.getReference("ImageList")


    // callback
    fun getListUsersFromRealTimeDatabase(
        onSuccess: (List<Music>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val musicList = mutableListOf<Music>()

                var i: Int = 1
                for (dataSnapshot in snapshot.children) {
                    val music = dataSnapshot.getValue(Music::class.java)
                    music?.let {
                        musicList.add(it)
                    }

                    Log.d("YYY", "musicList : " + musicList)
                    Log.d("RRR", "snapshot.children : " + snapshot.children)
                }
                onSuccess(musicList)

            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.toException())
            }
        })
    }

    // callback
    fun getListUsersFromRealTimeDatabase1(
        onSuccess: (List<Singer>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        myRef1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val singerList = mutableListOf<Singer>()
                for (dataSnapshot in snapshot.children) {
                    val singer = dataSnapshot.getValue(Singer::class.java)
                    singer?.let {
                        singerList.add(it)
                    }
                }
                onSuccess(singerList)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.toException())
            }
        })
    }

    // callback image
    fun getListImageFromRealTimeDatabase(
        onSuccess: (List<Photo>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        myRef2.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                val imageList = mutableListOf<Photo>()
                for (dataSnapshot in snapshot.children) {
                    val images = dataSnapshot.getValue(Photo::class.java)
                    images?.let {
                        imageList.add(it)
                    }
                }
                onSuccess(imageList)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.toException())
            }

        })
    }

}
