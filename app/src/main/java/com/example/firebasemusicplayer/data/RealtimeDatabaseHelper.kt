package com.example.firebasemusicplayer.data

import com.example.firebasemusicplayer.model.Music
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RealtimeDatabaseHelper {
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("Song")

    // callback
    fun getListUsersFromRealTimeDatabase(onSuccess: (List<Music>) -> Unit, onFailure: (Exception) -> Unit) {
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val musicList = mutableListOf<Music>()
                for (dataSnapshot in snapshot.children) {
                    val music = dataSnapshot.getValue(Music::class.java)
                    music?.let {
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
}
//class RealtimeDatabaseHelper {
//    private val database = FirebaseDatabase.getInstance()
//    private val myRef = database.getReference("Singer")
//
//    // callback
//    fun getListUsersFromRealTimeDatabase(
//        onSuccess: (List<Music>) -> Unit,
//        onFailure: (Exception) -> Unit
//    ) {
//        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val musicList = mutableListOf<Music>()
//                for (dataSnapshot in snapshot.children) {
//                    val music = dataSnapshot.getValue(Music::class.java)
//                    music?.let {
//                        musicList.add(it)
//                    }
//                }
//                onSuccess(musicList)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                onFailure(error.toException())
//            }
//        })
//    }
//
//    fun searchUsersFromRealTimeDatabase(
//        query: String,
//        onSuccess: (List<Music>) -> Unit,
//        onFailure: (Exception) -> Unit
//    ) {
//        val musicList = mutableListOf<Music>()
//
//        val queryRef = myRef.orderByChild("songName")
//            .startAt(query)
//            .endAt("$query\uf8ff")
//
//        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (ds in snapshot.children) {
//                    val music = ds.getValue(Music::class.java)
//                    music?.let {
//                        musicList.add(it)
//                    }
//                }
//                onSuccess(musicList)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                onFailure(error.toException())
//            }
//        })
//    }
//}