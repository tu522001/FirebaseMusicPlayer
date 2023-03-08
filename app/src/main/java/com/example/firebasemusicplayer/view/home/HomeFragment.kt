package com.example.firebasemusicplayer.view.home

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.adapter.MusicAdapter
import com.example.firebasemusicplayer.data.RealtimeDatabaseHelper
import com.example.firebasemusicplayer.model.Music
import com.google.firebase.database.*
import com.example.firebasemusicplayer.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var searchView: SearchView? = null
    private var musicAdapter: MusicAdapter? = null
    private var musicList: ArrayList<Music>? = null
    private var music: Music? = null
    private var database: FirebaseDatabase? = null
    private var myRef: DatabaseReference? = null

    //
    private lateinit var realtimeDatabaseHelper: RealtimeDatabaseHelper

    //    private val musicList: MutableList<Music> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        searchView = binding.searchView.findViewById(R.id.searchView)
        recyclerView = binding.recyclerView.findViewById(R.id.recyclerView)

        realtimeDatabaseHelper = RealtimeDatabaseHelper()
//        getListUsersFromRealTimeDatabase()
        getListUsers()
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = linearLayoutManager

        // DividerItemDecoration là một lớp trong Android cung cấp cách để vẽ một chia cách giữa các item trong RecyclerView
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerView?.addItemDecoration(dividerItemDecoration)
        musicList = ArrayList<Music>()
        Log.d("QQQ", "arrayList" + musicList!!.size)
        musicAdapter = MusicAdapter(musicList)
        recyclerView?.adapter = musicAdapter

        onItemClick()
        return binding.root
    }

    private fun getListUsers() {
        realtimeDatabaseHelper.getListUsersFromRealTimeDatabase(
            onSuccess = { musicList ->
                // Update UI with musicList
                this.musicList?.clear()
                this.musicList?.addAll(musicList)
                musicAdapter?.notifyDataSetChanged()
                Log.d("EEE", "arrayList" + musicList!!.size)

            },
            onFailure = { exception ->
                Toast.makeText(context, "Get list users failed", Toast.LENGTH_SHORT).show()
            }
        )
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
//                musicAdapter?.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Toast.makeText(context, "Get list users faild", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        Log.d("WWW", "HomeFramgne    database : " + database)
//        Log.d("WWW", "HomeFramgne    myRef : " + myRef)
//    }

    // hàm xử lý sự kiện click vào các item ở trong recyclerView để phát nhạc
    private fun onItemClick() {
        // position bài hát để phát trong RecyclerView
        musicAdapter?.setOnItemClickListener(object : MusicAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
//                    mediaPlayer = MediaPlayer()
                Log.d("BBB", "" + position)

                Log.d("ZZZ", "HomeFramgne    Position : " + position)
                Log.d("ZZZ", "HomeFramgnet   songURL : " + musicList!!.get(position!!).songURL)
//                    if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
//                        mediaPlayer!!.stop()
//                        mediaPlayer!!.reset()
//                    }
//                    try {
//                        mediaPlayer!!.setDataSource(musicList!!.get(position).songURL)
//                        mediaPlayer!!.setOnPreparedListener(MediaPlayer.OnPreparedListener { mediaPlayer -> mediaPlayer.start() })
//                        mediaPlayer!!.prepare()
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }

//                    findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
//                    findNavController().navigate(R.id.action_homeFragment_to_screenFragment)

//                findNavController().navigate(R.id.action_homeFragment_to_screenFragment)
//
//                //            Truyền dữ liệu theo Bundle
//                val bundle = Bundle().apply {
//                    putInt("position", position)
//                }
//                val fragment = ScreenFragment()
//                fragment.arguments = bundle

                val bundle = Bundle().apply {
                    putInt("Key_position", position)
                }
                findNavController().navigate(R.id.action_homeFragment_to_screenFragment, bundle)


            }
        })
    }

}