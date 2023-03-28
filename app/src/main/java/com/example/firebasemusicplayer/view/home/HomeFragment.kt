package com.example.firebasemusicplayer.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.adapter.MusicAdapter
import com.example.firebasemusicplayer.adapter.SingerAdapter
import com.example.firebasemusicplayer.data.RealtimeDatabaseHelper
import com.example.firebasemusicplayer.databinding.FragmentHomeBinding
import com.example.firebasemusicplayer.model.Music
import com.example.firebasemusicplayer.model.Singer
import com.google.firebase.database.*


class HomeFragment : Fragment() {

    private var recyclerViewMusic: RecyclerView? = null
    private var recyclerViewSinger: RecyclerView? = null

    private var searchView: SearchView? = null
    private var musicAdapter: MusicAdapter? = null
    private var singerAdapter : SingerAdapter?= null

    private var musicList: ArrayList<Music>? = null
    private var singerList: ArrayList<Singer>? = null

    private var database: FirebaseDatabase? = null
    private var database1: FirebaseDatabase? = null

    private var myRef: DatabaseReference? = null
    private var myRef1: DatabaseReference? = null

    private lateinit var realtimeDatabaseHelper: RealtimeDatabaseHelper
    private lateinit var realtimeDatabaseHelper1: RealtimeDatabaseHelper

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
//
////        searchView = binding.searchView.findViewById(R.id.searchView)
        recyclerViewMusic = binding.recyclerView.findViewById(R.id.recyclerView)
        recyclerViewSinger = binding.recyclerView.findViewById(R.id.singerRecyclerView)

//        recyclerViewMusic?.setHasFixedSize(true)
//        recyclerViewMusic?.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//
//
//
//
//
//
//
        realtimeDatabaseHelper = RealtimeDatabaseHelper()
        realtimeDatabaseHelper1 = RealtimeDatabaseHelper()

        database = FirebaseDatabase.getInstance()
        database1 = FirebaseDatabase.getInstance()

        myRef = database?.getReference("Song")
        myRef1 = database1?.getReference("Singer")

        getListUsers()
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerViewMusic?.layoutManager = linearLayoutManager

        getListUsers1()
        val linearLayoutManager1 = LinearLayoutManager(context)
        recyclerViewSinger?.layoutManager = linearLayoutManager1

//        // DividerItemDecoration là một lớp trong Android cung cấp cách để vẽ một chia cách giữa các item trong RecyclerView
//        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//        recyclerViewMusic?.addItemDecoration(dividerItemDecoration)

        musicList = ArrayList<Music>()
        singerList = ArrayList<Singer>()

        Log.d("QQQ", "arrayList" + musicList!!.size)
        musicAdapter = MusicAdapter(musicList)
        recyclerViewMusic?.adapter = musicAdapter

//        Log.d("QQQ", "arrayList" + singerList!!.size)
        singerAdapter = SingerAdapter(singerList)
        recyclerViewSinger?.adapter = singerAdapter

//        onItemClick()

//                searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            // onQueryTextSubmit được gọi khi người dùng hoàn tất việc nhập văn bản và muốn tìm kiếm
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return false
//            }
//
//            // onQueryTextChange được gọi mỗi khi nội dung trong thanh tìm kiếm thay đổi.
//            override fun onQueryTextChange(newText: String): Boolean {
//                musicList!!.clear()
//                return if (newText.isEmpty()) {
//                    getListUsers()
//                    true
//                } else {
//                    search(newText)
//                    true
//                }
//            }
//        })

//        // RecyclerViewMusic
//        recyclerViewMusic = binding.recyclerView.findViewById(R.id.recyclerView)
//        recyclerViewMusic?.setHasFixedSize(true)
//        recyclerViewMusic?.setLayoutManager(
//            LinearLayoutManager(
//                context,
//                LinearLayoutManager.HORIZONTAL,
//                false
//            )
//        )

//        val musicList= ArrayList<Music>()
//
//        musicList.add(Music(1,"a","a","a","a"))
//        musicList.add(Music(2,"b","b","b","b"))
//        musicList.add(Music(3,"c","c","c","c"))
//        musicList.add(Music(4,"d","d","d","d"))
//        musicList.add(Music(5,"e","e","e","e"))
//
//         musicAdapter = MusicAdapter(musicList)
//
//        recyclerViewMusic?.setAdapter(musicAdapter)
//
//


//        // RecyclerViewSinger
        recyclerViewSinger = binding.singerRecyclerView.findViewById(R.id.singerRecyclerView)
        recyclerViewSinger?.setHasFixedSize(true)
        recyclerViewSinger?.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
//
//        val singerList= ArrayList<Singer>()
//
//        singerList.add(Singer(1,"a","a","a","a"))
//        singerList.add(Singer(2,"b","b","b","b"))
//        singerList.add(Singer(3,"c","c","c","c"))
//        singerList.add(Singer(1,"a","a","a","a"))
//        singerList.add(Singer(2,"b","b","b","b"))
//        singerList.add(Singer(3,"c","c","c","c"))
//        singerList.add(Singer(1,"a","a","a","a"))
//        singerList.add(Singer(2,"b","b","b","b"))
//        singerList.add(Singer(3,"c","c","c","c"))
//
         singerAdapter = SingerAdapter(singerList)

        recyclerViewSinger?.setAdapter(singerAdapter)

        return binding.root
    }
//
//    private fun search(query: String) {
//        val databaseReference = FirebaseDatabase.getInstance().getReference("Song")
//
//        databaseReference.orderByChild("songName")
//            .startAt(query)
//            .endAt(query + "\uf8ff")
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    musicList!!.clear()
//                    for (postSnapshot in snapshot.children) {
//                        val music = postSnapshot.getValue(Music::class.java)
//                        musicList!!.add(music!!)
//                    }
//                    musicAdapter!!.notifyDataSetChanged()
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    Log.e("DDD", "onCancelled", databaseError.toException())
//                }
//            })
//    }

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

    private fun getListUsers1() {

        realtimeDatabaseHelper1.getListUsersFromRealTimeDatabase1(
            onSuccess = { singerList ->
                // Update UI with musicList
                this.singerList?.clear()
                this.singerList?.addAll(singerList)
                singerAdapter?.notifyDataSetChanged()
                Log.d("EEE", "singerList" + singerList!!.size)

            },
            onFailure = { exception ->
                Toast.makeText(context, "Get list users failed", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // hàm xử lý sự kiện click vào các item ở trong recyclerView để phát nhạc
//    private fun onItemClick() {
//        // position bài hát để phát trong RecyclerView
//        musicAdapter?.setOnItemClickListener(object : MusicAdapter.OnItemClickListener {
//            override fun onClick(position: Int) {
//                val music = musicList!![position]
//                val bundle = Bundle().apply {
//                    putInt("Key_position", position)
//                    putString("Key_song_name", musicList!![position].songName)
//                    putString("Key_image_URL", musicList!![position].imageURL)
//                    putString("Key_singer_name", musicList!![position].singerName)
//                }
//                findNavController().navigate(R.id.action_homeFragment_to_screenFragment, bundle)
//
//
//            }
//        })
//    }


}
