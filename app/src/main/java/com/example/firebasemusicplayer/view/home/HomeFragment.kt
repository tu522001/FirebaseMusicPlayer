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
    private var database: FirebaseDatabase? = null
    private var myRef: DatabaseReference? = null

    private lateinit var realtimeDatabaseHelper: RealtimeDatabaseHelper

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
        database = FirebaseDatabase.getInstance()
        myRef = database?.getReference("Song")

        getListUsers()
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = linearLayoutManager

        // DividerItemDecoration l?? m???t l???p trong Android cung c???p c??ch ????? v??? m???t chia c??ch gi???a c??c item trong RecyclerView
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerView?.addItemDecoration(dividerItemDecoration)
        musicList = ArrayList<Music>()
        Log.d("QQQ", "arrayList" + musicList!!.size)
        musicAdapter = MusicAdapter(musicList)
        recyclerView?.adapter = musicAdapter

        onItemClick()

                searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // onQueryTextSubmit ???????c g???i khi ng?????i d??ng ho??n t???t vi???c nh???p v??n b???n v?? mu???n t??m ki???m
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            // onQueryTextChange ???????c g???i m???i khi n???i dung trong thanh t??m ki???m thay ?????i.
            override fun onQueryTextChange(newText: String): Boolean {
                musicList!!.clear()
                return if (newText.isEmpty()) {
                    getListUsers()
                    true
                } else {
                    search(newText)
                    true
                }
            }
        })
        return binding.root
    }

    private fun search(query: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Song")

        databaseReference.orderByChild("songName")
            .startAt(query)
            .endAt(query + "\uf8ff")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    musicList!!.clear()
                    for (postSnapshot in snapshot.children) {
                        val music = postSnapshot.getValue(Music::class.java)
                        musicList!!.add(music!!)
                    }
                    musicAdapter!!.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("DDD", "onCancelled", databaseError.toException())
                }
            })
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

    // h??m x??? l?? s??? ki???n click v??o c??c item ??? trong recyclerView ????? ph??t nh???c
    private fun onItemClick() {
        // position b??i h??t ????? ph??t trong RecyclerView
        musicAdapter?.setOnItemClickListener(object : MusicAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                val music = musicList!![position]
                val bundle = Bundle().apply {
                    putInt("Key_position", position)
                    putString("Key_song_name", musicList!![position].songName)
                    putString("Key_image_URL", musicList!![position].imageURL)
                    putString("Key_singer_name", musicList!![position].singerName)
                }
                findNavController().navigate(R.id.action_homeFragment_to_screenFragment, bundle)


            }
        })
    }


}
