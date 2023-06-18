package com.example.firebasemusicplayer.view.fragment.home

import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.firebasemusicplayer.view.listeners.OnClickListenerPlayMusic
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentHomeBinding
import com.example.firebasemusicplayer.model.entity.Music
import com.example.firebasemusicplayer.model.entity.Photo
import com.example.firebasemusicplayer.model.entity.Singer
import com.example.firebasemusicplayer.model.entity.User
import com.example.firebasemusicplayer.view.adapter.MusicAdapter
import com.example.firebasemusicplayer.view.adapter.PhotoAdapter
import com.example.firebasemusicplayer.view.adapter.SingerAdapter
import com.example.firebasemusicplayer.view.listeners.OnClickSingerInformation
import com.example.firebasemusicplayer.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*


class HomeFragment : Fragment(), OnClickListenerPlayMusic, OnClickSingerInformation {

    private val homeViewModel by viewModels<HomeViewModel>()
    private var photoAdapter: PhotoAdapter? = null
    private var musicList = mutableListOf<Music>()
    private var singerList = mutableListOf<Singer>()
    private var photoList = mutableListOf<Photo>()
    private lateinit var  musicAdapter : MusicAdapter
    private lateinit var singerAdapter : SingerAdapter
    private lateinit var user : User
    private lateinit var binding: FragmentHomeBinding
    private var mTimer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        initView()
        observeData()
        initData()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // onQueryTextSubmit được gọi khi người dùng hoàn tất việc nhập văn bản và muốn tìm kiếm
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            // onQueryTextChange được gọi mỗi khi nội dung trong thanh tìm kiếm thay đổi.
            override fun onQueryTextChange(newText: String): Boolean {
                musicList!!.clear()
                return if (newText.isEmpty()) {
                    homeViewModel.fetchAllSongsFromFirebase()
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

        return binding.root
    }

    private fun initView() {
        musicAdapter = MusicAdapter(requireContext(),this,musicList)
        singerAdapter = SingerAdapter(requireContext(),this,singerList)
        binding.rcvSong?.adapter = musicAdapter
        binding.rcvSinger?.adapter = singerAdapter

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
        }, 5000, 5000)

    }

    private fun observeData() {
        homeViewModel.listSong.observe(viewLifecycleOwner,::handleAllSongs)
        homeViewModel.listPhoto.observe(viewLifecycleOwner,::handleAllImages)
        homeViewModel.listSinger.observe(viewLifecycleOwner,::handleAllSingers)
    }

    private fun handleAllSingers(singers: List<Singer>) {
        this.singerList?.clear()
        this.singerList.addAll(singers)
        singerAdapter.notifyDataSetChanged()
    }

    private fun handleAllImages(photos: List<Photo>) {
        this.photoList?.clear()
        this.photoList?.addAll(photos)
        photoAdapter?.notifyDataSetChanged()
    }

    private fun handleAllSongs(songs: List<Music>) {
        this.musicList?.clear()
        this.musicList?.addAll(songs)
        musicAdapter?.notifyDataSetChanged()
    }

    private fun initData() {
        homeViewModel.apply {
            fetchAllSongsFromFirebase()
            fetchAllImagesFromFirebase()
            fetchAllSingersFromFirebase()
        }
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
    override fun onClickPlayMusic(position: Int) {
        val musics = musicList!![position]
        val bundle = Bundle().apply {
            putInt("Key_position", position)
            putString("Key_song_name", musics.songName)
            putString("Key_image_URL", musics.imageURL)
            putString("Key_singer_name", musics.singerName)
        }
        findNavController().navigate(R.id.action_homeFragment_to_screenFragment, bundle)
    }

    override fun onClickSingerInformation(position: Int) {
        val singer = singerList!![position]
        Log.d("RRR","Data  HOME_FRAGMENT: singer : "+singer.data+" , height : "+singer.height+" , placeOfBirth : "+singer.placeOfBirth+" , sex : "+singer.sex+" , yearOfOperation : "+singer.singerName)

        val bundle = Bundle().apply {
            putString("Key_image_URL", singer.imageURL)
            putString("Key_singer_name", singer.singerName)
            putString("Key_data", singer.data)
            putString("Key_height", singer.height)
            putString("Key_placeOfBirth", singer.placeOfBirth)
            putString("Key_sex", singer.sex)
        }
        findNavController().navigate(R.id.action_homeFragment_to_singerInformationFragment, bundle)
    }
}
