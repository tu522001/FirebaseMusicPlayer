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
import java.io.IOException


class ScreenFragment : Fragment() {

    private var number: Int? = null
    private var mediaPlayer: MediaPlayer? = null
    private var musicList: ArrayList<Music>? = null

    //    private var music: Music? = null
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
        realtimeDatabaseHelper.getListUsersFromRealTimeDatabase(
            onSuccess = { musicList ->

                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setDataSource(musicList!!.get(number!!).songURL)

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
                binding.btnStop.setOnClickListener{
                    mediaPlayer!!.stop()
                    mediaPlayer!!.release()
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

    // button Stop
    private fun onClickStopButton() {
        binding.btnStop.setOnClickListener {
////            ACTION_STOP
//            Log.d("SSS", "Stop : Vào")
//
////            imageView2.clearAnimation()
//            mediaPlayer!!.stop()
//            mediaPlayer!!.release()
////            btn_play.setImageResource(R.drawable.ic_play)
////            khoiTao()
//            btn_play.setImageResource(R.drawable.ic_play)
        }
    }

    // button Skip
    private fun onClickSkipButton() {
        binding.btnSkip.setOnClickListener {
//            ACTION_SKIP
            Log.d("SSS", "Skip : Vào")
        }
    }

    // button Previous
    private fun onClickPreviousButton() {
        binding.btnPrevious.setOnClickListener {
//            ACTION_PREVIOUS
            Log.d("SSS", "Previous : Vào")
        }
    }

//    private fun handleClickMusic() {
////        var i = null
//        when (i) {
//            1 -> onClickPlayrButton()
//            2 -> onClickSkipButton()
//            3 -> onClickStopButton()
//            4 -> onClickPreviousButton()
//            else -> {
//
//            }
//        }
//    }

}