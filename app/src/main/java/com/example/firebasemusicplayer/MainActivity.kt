package com.example.firebasemusicplayer

import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasemusicplayer.adapter.MusicAdapter
import com.example.firebasemusicplayer.model.Music
import com.google.firebase.database.*
import java.io.IOException

/**
 *
 * MainActivity
 *
 * */

class MainActivity : AppCompatActivity() {

//    private var recyclerView: RecyclerView? = null
//    private var musicAdapter: MusicAdapter? = null
//    private var musicList: ArrayList<Music>? = null
//    private var music: Music? = null
//    private var mediaPlayer: MediaPlayer? = null
//    private var searchView: SearchView? = null
//    private var database: FirebaseDatabase? = null
//    private var myRef: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        init()
//        getListUsersFromRealTimeDatabase()
//        val linearLayoutManager = LinearLayoutManager(this)
//        recyclerView?.layoutManager = linearLayoutManager
//
//        // DividerItemDecoration là một lớp trong Android cung cấp cách để vẽ một chia cách giữa các item trong RecyclerView
//        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
//        recyclerView?.addItemDecoration(dividerItemDecoration)
//
//        musicList = ArrayList<Music>()
//        musicAdapter = MusicAdapter(musicList)
//        recyclerView?.adapter = musicAdapter
//        onItemClick()
//
//        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            // onQueryTextSubmit được gọi khi người dùng hoàn tất việc nhập văn bản và muốn tìm kiếm
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return false
//            }
//
//            // onQueryTextChange được gọi mỗi khi nội dung trong thanh tìm kiếm thay đổi.
//            override fun onQueryTextChange(newText: String): Boolean {
//                musicList!!.clear()
//                return if (newText == "") {
//                    getListUsersFromRealTimeDatabase()
//                    true
//                } else {
//                    search(newText)
//                    true
//                }
//            }
//        })
//    }
//
//    /**      Search with attribute "songName" **/
//    private fun search(s: String) {
////       code chưa tối ưu
////       val query = myRef!!.orderByChild("songName").equalTo(s)
//
////      code tối ưu
//        val query = myRef?.let { ref ->
//            ref.orderByChild("songName")
//                .equalTo(s) // tạo ra 1 khối ref xong trong khối ấy sử dụng các function or method
//        }
//
////Add a listener to the query
//        query?.addListenerForSingleValueEvent(object : ValueEventListener {
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                musicList!!.clear()
//                for (userSnapshot in dataSnapshot.children) {
//                    val music: Music? = userSnapshot.getValue(Music::class.java)
//                    musicList!!.add(music!!)
//                }
//                musicAdapter!!.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.d("FirebaseSearch", "Error while searching: " + databaseError.message)
//            }
//        })
//
//    }
//
//    private fun init() {
//        searchView = findViewById<SearchView>(R.id.searchView)
//        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//    }
//
//
//    private fun getListUsersFromRealTimeDatabase() {
//        database = FirebaseDatabase.getInstance()
//        myRef = database!!.getReference("Singer")
//
//        myRef!!.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (dataSnapshot in snapshot.children) {
//                    music = dataSnapshot.getValue(Music::class.java)
//                    Log.d("AAA",""+music)
//                    musicList?.add(music!!)
//                }
//                musicAdapter?.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Toast.makeText(this@MainActivity, "Get list users faild", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    // hàm xử lý sự kiện click vào các item ở trong recyclerView để phát nhạc
//    private fun onItemClick() {
//        // position bài hát để phát trong RecyclerView
//        musicAdapter?.setOnItemClickListener(object : MusicAdapter.OnItemClickListener {
//            override fun onClick(position: Int) {
//                mediaPlayer = MediaPlayer()
//                Log.d("BBB", musicList?.get(position)!!.songURL)
//                if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
//                    mediaPlayer!!.stop()
//                    mediaPlayer!!.reset()
//                }
//                try {
//                    mediaPlayer!!.setDataSource(musicList!!.get(position).songURL)
//                    mediaPlayer!!.setOnPreparedListener(OnPreparedListener { mediaPlayer -> mediaPlayer.start() })
//                    mediaPlayer!!.prepare()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        })
//
////        Log.d("VAO","0+VAO")
    }


}

