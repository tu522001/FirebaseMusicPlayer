package com.example.firebasemusicplayer.view.user.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentHomeBinding
import com.example.firebasemusicplayer.model.entity.Music
import com.example.firebasemusicplayer.model.entity.Photo
import com.example.firebasemusicplayer.model.entity.Singer
import com.example.firebasemusicplayer.model.data.RealtimeDatabaseHelper
import com.example.firebasemusicplayer.model.entity.User
import com.example.firebasemusicplayer.view.adapter.MusicAdapter
import com.example.firebasemusicplayer.view.adapter.PhotoAdapter
import com.example.firebasemusicplayer.view.adapter.SingerAdapter
import com.facebook.FacebookSdk.getApplicationContext
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class HomeFragment : Fragment() {

    private var recyclerViewMusic: RecyclerView? = null
    private var recyclerViewSinger: RecyclerView? = null
    private var searchView: SearchView? = null
    private var musicAdapter: MusicAdapter? = null
    private var singerAdapter: SingerAdapter? = null

    private var mViewPager2: ViewPager2? = null
    private var mbottomNavigationView: BottomNavigationView? = null
    private var photoAdapter: PhotoAdapter? = null
    private var musicList: ArrayList<Music>? = null
    private var singerList: ArrayList<Singer>? = null
    private var photoList: ArrayList<Photo>? = null
    private var database: FirebaseDatabase? = null
    private var database1: FirebaseDatabase? = null
    private var databaseListMusic: FirebaseDatabase? = null
    private var myRef: DatabaseReference? = null
    private var myRef1: DatabaseReference? = null
    private var myRef2: DatabaseReference? = null

    private lateinit var realtimeDatabaseHelper: RealtimeDatabaseHelper
    private lateinit var realtimeDatabaseHelper1: RealtimeDatabaseHelper
    private lateinit var realtimeDatabaseHelper2: RealtimeDatabaseHelper
    private lateinit var user : User
    private lateinit var url_avatar: String

    private lateinit var binding: FragmentHomeBinding

    private var mTimer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        searchView = binding.searchView.findViewById(R.id.searchView)
        recyclerViewMusic = binding.recyclerView.findViewById(R.id.recyclerView)
        recyclerViewSinger = binding.recyclerView.findViewById(R.id.singerRecyclerView)

        realtimeDatabaseHelper = RealtimeDatabaseHelper()
        realtimeDatabaseHelper1 = RealtimeDatabaseHelper()
        realtimeDatabaseHelper2 = RealtimeDatabaseHelper()

        database = FirebaseDatabase.getInstance()
        database1 = FirebaseDatabase.getInstance()

        myRef = database?.getReference("Song")
        myRef1 = database1?.getReference("Singer")
        myRef2 = databaseListMusic?.getReference("ImageList")

        getListUsers()
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerViewMusic?.layoutManager = linearLayoutManager

        getListUsers1()
        val linearLayoutManager1 = LinearLayoutManager(context)
        recyclerViewSinger?.layoutManager = linearLayoutManager1


        // RecyclerViewSinger
        recyclerViewSinger = binding.singerRecyclerView.findViewById(R.id.singerRecyclerView)
        recyclerViewSinger?.setHasFixedSize(true)
        recyclerViewSinger?.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )

        musicList = ArrayList<Music>()
        singerList = ArrayList<Singer>()
        photoList = ArrayList<Photo>()


        Log.d("QQQ", "arrayList" + musicList!!.size)
        musicAdapter = MusicAdapter(musicList)
        recyclerViewMusic?.adapter = musicAdapter

        Log.d("QQQ", "arrayList" + singerList!!.size)
        singerAdapter = SingerAdapter(singerList)
        recyclerViewSinger?.adapter = singerAdapter

        onItemClick()

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // onQueryTextSubmit được gọi khi người dùng hoàn tất việc nhập văn bản và muốn tìm kiếm
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            // onQueryTextChange được gọi mỗi khi nội dung trong thanh tìm kiếm thay đổi.
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


        // gọi giá trị email của firebase ra thông qua phương thức dưới đây
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email
        println(userEmail)

//        var user : User = User()

        // lấy dữ liệu position từ HomeFramgent sang ScreenFragment

        val bundle = arguments
// Kiểm tra nếu bundle không null và có chứa key "Key_url_avatar_facebook"

        if (bundle != null && bundle.containsKey("Key_url_avatar_facebook")) {
            val url_avatar = bundle.getString("Key_url_avatar_facebook")
            // Sử dụng Glide để hiển thị ảnh đại diện
            Glide.with(this)
                .load(url_avatar)
                .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
                .into(binding.imgAvatarFacebook)

            binding.imgAvatarFacebook.setOnClickListener {
                if (bundle != null && bundle.containsKey("Key_url_avatar_facebook")) {
                    Glide.with(this)
                        .load(url_avatar)
                        .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
                        .into(binding.imgAvatarFacebook)
                    findNavController().navigate(R.id.action_homeFragment_to_facebookFragment3)
                }
            }
        }else{
            user = User("admin123@gmail.com","admin123")
            if ((bundle == null) && (userEmail != user.email)){
                Glide.with(this)
                    .load(R.drawable.user).apply(RequestOptions().override(Target.SIZE_ORIGINAL))
                    .into(binding.imgAvatarFacebook)
                binding.imgAvatarFacebook.setOnClickListener {
                    findNavController().navigate(R.id.action_homeFragment_to_facebookFragment3)
                }


            }else if (user.email == "admin123@gmail.com" && user.password == "admin123" ){
                Glide.with(this)
                    .load(R.drawable.administrator).apply(RequestOptions().override(Target.SIZE_ORIGINAL))
                    .into(binding.imgAvatarFacebook)
                binding.imgAvatarFacebook.setOnClickListener {
                    findNavController().navigate(R.id.action_homeFragment_to_adminFragment2)
                }
            }
        }



        getListImage()
//        photoList = getListImage()
        photoAdapter = PhotoAdapter(requireContext(), photoList)
        binding.viewpager.adapter = photoAdapter

        binding.circleIndicator.setViewPager(binding.viewpager)
        photoAdapter!!.registerDataSetObserver(binding.circleIndicator.dataSetObserver)

        // Init timer
        if (mTimer == null) {
            mTimer = Timer()
        }

        mTimer!!.schedule(object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    var currentItem: Int = binding.viewpager.currentItem
                    val totalItem = photoList!!.size - 1
                    if (currentItem < totalItem) {
                        currentItem++
                        binding.viewpager.currentItem = currentItem
                    } else {
                        binding.viewpager.currentItem = 0
                    }
                }
            }
        }, 2000, 5000)

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

    private fun getListImage() {

        realtimeDatabaseHelper2.getListImageFromRealTimeDatabase(
            onSuccess = { photoList ->
                // Update UI with musicList
                this.photoList?.clear()
                this.photoList?.addAll(photoList)
                photoAdapter?.notifyDataSetChanged()
                Log.d("EEE", "mListPhoto" + photoList!!.size)

            },
            onFailure = { exception ->
                Toast.makeText(context, "Get list users failed", Toast.LENGTH_SHORT).show()
            }
        )
    }

    //     hàm xử lý sự kiện click vào các item ở trong recyclerView để phát nhạc
    private fun onItemClick() {
        // position bài hát để phát trong RecyclerView
        musicAdapter?.setOnItemClickListener(object : MusicAdapter.OnItemClickListener {
            override fun onClick(position: Int) {

                val musics = musicList!![position]
                val bundle = Bundle().apply {
                    putInt("Key_position", position)
                    putString("Key_song_name", musics.songName)
                    putString("Key_image_URL", musics.imageURL)
                    putString("Key_singer_name", musics.singerName)
                }
                findNavController().navigate(R.id.action_homeFragment_to_screenFragment, bundle)


            }
        })
    }


}
