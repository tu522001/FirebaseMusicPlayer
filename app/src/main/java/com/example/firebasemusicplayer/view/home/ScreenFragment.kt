package com.example.firebasemusicplayer.view.home

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.data.RealtimeDatabaseHelper
import com.example.firebasemusicplayer.databinding.FragmentScreenBinding
import com.example.firebasemusicplayer.model.Music
import com.google.firebase.database.*
import java.io.IOException


class ScreenFragment : Fragment() {

    private var number: Int? = null
    private var mediaPlayer: MediaPlayer? = null
    private var musicList: ArrayList<Music>? = null
    private var music: Music? = null

    private var database: FirebaseDatabase? = null
    private var myRef: DatabaseReference? = null


    private lateinit var realtimeDatabaseHelper: RealtimeDatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentScreenBinding>(
            inflater,
            R.layout.fragment_screen,
            container,
            false
        )


        realtimeDatabaseHelper = RealtimeDatabaseHelper()
//
//        doSomethingWithListUsers()

        realtimeDatabaseHelper.getListUsersFromRealTimeDatabase(
            onSuccess = { musicList ->
//                this.musicList?.clear()
//                this.musicList?.addAll(musicList)
//                this.musicList?.addAll(musicList)

                binding.btnPlay.setOnClickListener {
                    Log.d("ZZZ", "ScreenFragment    Position : " + number)
                    Log.d("ZZZ", "ScreenFragment    songURL : " + musicList!!.get(number!!).songURL)
                    mediaPlayer = MediaPlayer()
                    if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                        mediaPlayer!!.stop()
                        mediaPlayer!!.reset()
                    }
                    try {
                        mediaPlayer!!.setDataSource(musicList!!.get(number!!).songURL)
                        mediaPlayer!!.setOnPreparedListener(MediaPlayer.OnPreparedListener { mediaPlayer -> mediaPlayer.start() })
                        mediaPlayer!!.prepare()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

                Log.d("EEE","ScreenFragment arrayList : "+ musicList!!.size)
            },
            onFailure = { exception ->
                // Handle error
            }
        )
//        musicList = ArrayList<Music>()
        onClickPosition()


        return binding.root
    }

    private fun onClickPosition() {
        // Lấy position trong RecyclerView
        val bundle = arguments
        number = bundle?.getInt("Key_position")
        Log.d("AAAS", "Position : " + number)
    }

//    private fun getListUsersFromRealTimeDatabase() {
//        database = FirebaseDatabase.getInstance()
//        myRef = database!!.getReference("Singer")
//        myRef!!.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (dataSnapshot in snapshot.children) {
//                    music = dataSnapshot.getValue(Music::class.java)
//                    musicList?.add(music!!)
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Toast.makeText(context, "Get list users faild", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        Log.d("WWW", "ScreenFragment    database : " + database)
//        Log.d("WWW", "ScreenFragment    myRef : " + myRef)
//    }

    private fun doSomethingWithListUsers() {

    }
}