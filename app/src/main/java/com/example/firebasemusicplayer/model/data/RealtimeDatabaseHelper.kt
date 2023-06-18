package com.example.firebasemusicplayer.model.data

import com.example.firebasemusicplayer.model.entity.Music
import com.example.firebasemusicplayer.model.entity.Photo
import com.example.firebasemusicplayer.model.entity.Singer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


object RealtimeDatabaseHelper {
    private val database = FirebaseDatabase.getInstance()

    private val myRef = database.getReference("Song")
    private val myRef1 = database.getReference("Singer")
    private val myRef2 = database.getReference("ImageList")

    fun getAllSongsFromFirebase(
        onSuccess: (List<Music>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val musicList = mutableListOf<Music>()

                for (dataSnapshot in snapshot.children) {
                    val music = dataSnapshot.getValue(Music::class.java)
                    music?.let {
                        // kiểm tra và chuyển đổi các thuộc tính số nguyên nếu cần thiết
                        it.id = it.id.toString().toIntOrNull() ?: 0
                        // thêm đối tượng Music vào danh sách
                        musicList.add(it)
                    }
                }
                onSuccess(musicList)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.toException())
            }
        })
    }



    // callback
    fun getAllSingersFromFirebase(
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
    fun getAllImagesFromFirebase(
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
