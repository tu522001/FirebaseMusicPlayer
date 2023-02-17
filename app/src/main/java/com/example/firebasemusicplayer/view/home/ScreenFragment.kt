package com.example.firebasemusicplayer.view.home

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.data.RealtimeDatabaseHelper
import com.example.firebasemusicplayer.databinding.FragmentScreenBinding
import com.example.firebasemusicplayer.model.Music
import kotlinx.android.synthetic.main.fragment_screen.*
import java.io.IOException


class ScreenFragment : Fragment() {

    private var number: Int = 0
    private var mediaPlayer: MediaPlayer? = null
    private var musicList: ArrayList<Music>? = null
    private var music: Music? = null

    //
//    private var database: FirebaseDatabase? = null
//    private var myRef: DatabaseReference? = null
    private lateinit var binding: FragmentScreenBinding
//    private var i by Delegates.notNull<Int>()

//    companion object {
//        private const val ACTION_PLAY = 1
//        private const val ACTION_SKIP = 2
//        private const val ACTION_STOP = 3
//        private const val ACTION_PREVIOUS = 4
//    }


    private lateinit var realtimeDatabaseHelper: RealtimeDatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate<FragmentScreenBinding>(
            inflater,
            R.layout.fragment_screen,
            container,
            false
        )


        realtimeDatabaseHelper = RealtimeDatabaseHelper()
//
        doSomethingWithListUsers()


//        musicList = ArrayList<Music>()
        onClickPosition()


        return binding.root
    }

    private fun onClickPosition() {
        // Lấy position trong RecyclerView
        val bundle = arguments
        number = bundle?.getInt("Key_position")!!
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
        realtimeDatabaseHelper.getListUsersFromRealTimeDatabase(
            onSuccess = { musicList ->

                music = musicList.get(number!!)
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setDataSource(musicList!!.get(number!!).songURL)

                // Khai báo biến để lưu trạng thái đang phát nhạc
                var isPlaying = false


//Button Play
                binding.btnPlay.setOnClickListener {
                    if (mediaPlayer!!.isPlaying) {
                        // Nếu đang phát -> pause -> đổi hình play
                        mediaPlayer!!.pause()
                        binding.btnPlay.setImageResource(R.drawable.ic_play)
                    } else {
                        /**
                         *   (+) "mediaPlayer!!.currentPosition" : là vị trí hiện tại của mediaPlayer trong quá trình phát nhạc, tính bằng mili giây (ms).
                         *   Nếu giá trị này lớn hơn 0, tức là mediaPlayer đã được phát ít nhất một phần của bài hát.
                         *
                         *   (+) "mediaPlayer!!.duration" : là thời gian của bài hát, cũng tính bằng mili giây (ms).
                         *   Nếu giá trị này khác với mediaPlayer!!.currentPosition, tức là chưa phát hết bài hát, thì mediaPlayer đang trong trạng thái tạm dừng.
                         *
                         *   (+) "mediaPlayer!!.currentPosition > 0 && mediaPlayer!!.duration != mediaPlayer!!.currentPosition".
                         *   Nếu điều kiện đúng, thì mediaPlayer đang trong trạng thái tạm dừng và chúng ta sẽ gọi mediaPlayer.start() để tiếp tục phát nhạc từ vị trí hiện tại.
                         * */
                        if (mediaPlayer!!.currentPosition > 0 && mediaPlayer!!.duration != mediaPlayer!!.currentPosition) {
                            // Đang tạm dừng -> tiếp tục phát
                            mediaPlayer!!.start()
                        } else {
                            // Đang dừng hoặc chưa bắt đầu -> bắt đầu phát
                            try {
                                mediaPlayer!!.prepare()
                                mediaPlayer!!.setOnPreparedListener(MediaPlayer.OnPreparedListener { mediaPlayer ->
                                    mediaPlayer!!.start()
                                })
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                        binding.btnPlay.setImageResource(R.drawable.ic_pause)
                    }
                }


// Button Stop
                binding.btnStop.setOnClickListener {
                    if (mediaPlayer!!.isPlaying) {
                        mediaPlayer!!.stop()
                        mediaPlayer!!.prepare()
                    }
                }


// Button Next
                binding.btnNext.setOnClickListener {
                    if (number!! >= musicList.size - 1) {
                        number = 0
                    } else {
                        number++
                    }
                    music = musicList.get(number!!)

                    // xử lý sự kiện để không bị hát trồng lên nhau
                    if (mediaPlayer!!.isPlaying) {
                        mediaPlayer!!.stop()
                    }
                    mediaPlayer!!.reset()
                    mediaPlayer!!.setDataSource(musicList!!.get(number!!).songURL)
                    mediaPlayer!!.prepare()
                    mediaPlayer!!.start()
                    // Đổi hình ảnh của nút "Play" thành hình "Pause"
                    binding.btnPlay.setImageResource(R.drawable.ic_pause)
                }


// Button Previous
                binding.btnPrevious.setOnClickListener{
                    if (number!! <= 0) {
                        number = musicList.size - 1
                    } else {
                        number--
                    }
                    music = musicList.get(number!!)

                    // xử lý sự kiện để không bị hát trồng lên nhau
                    if (mediaPlayer!!.isPlaying) {
                        mediaPlayer!!.stop()
                    }
                    mediaPlayer!!.reset()
                    mediaPlayer!!.setDataSource(musicList!!.get(number!!).songURL)
                    mediaPlayer!!.prepare()
                    mediaPlayer!!.start()
                    // Đổi hình ảnh của nút "Play" thành hình "Pause"
                    binding.btnPlay.setImageResource(R.drawable.ic_pause)
                }

                Log.d("EEE", "ScreenFragment arrayList : " + musicList!!.size)
            },
            onFailure = { exception ->
                // Handle error
            }
        )
    }

    // button Play
//    private fun onClickPlayrButton() {
//        mediaPlayer = MediaPlayer()
//        binding.btnPlay.setOnClickListener {
////            ACTION_PLAY
////            Log.d("ZZZ", "ScreenFragment    Position : " + number)
////            Log.d("ZZZ", "ScreenFragment    songURL : " + musicList!!.get(number!!).songURL)
//
////            if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
////                mediaPlayer!!.stop()
////                mediaPlayer!!.reset()
////            }
//            try {
//                mediaPlayer!!.setDataSource(musicList!!.get(number!!).songURL)
//                mediaPlayer!!.setOnPreparedListener(MediaPlayer.OnPreparedListener { mediaPlayer -> mediaPlayer.start() })
//                mediaPlayer!!.prepare()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//    }


}