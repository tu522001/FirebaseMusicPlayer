package com.example.firebasemusicplayer.view.home

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.data.RealtimeDatabaseHelper
import com.example.firebasemusicplayer.databinding.FragmentScreenBinding
import com.example.firebasemusicplayer.model.Music
import java.io.IOException
import java.text.SimpleDateFormat


class ScreenFragment : Fragment() {

    private var count : Int = 0
    private var number: Int = 0
    private lateinit var song_name: String
    private lateinit var image_url: String
    private lateinit var singer_name: String
    private var mediaPlayer: MediaPlayer? = null
    private var music: Music? = null
    private lateinit var binding: FragmentScreenBinding
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
        doSomethingWithListUsers()
        onClickPosition()

//        binding.imgBtnLogoutHome.setOnClickListener{
//            findNavController().navigate(R.id.action_screenFragment_to_homeFragment)
//        }

        binding.btnLike.setOnClickListener{
            count++
            if (count % 2 == 1){
                binding.btnLike.setImageResource(R.drawable.ic_favorite)
            }else{
                binding.btnLike.setImageResource(R.drawable.ic_favorite_border)
            }
        }

        return binding.root
    }

    private fun onClickPosition() {
        // L???y position trong RecyclerView
        val bundle = arguments

        // l???y d??? li???u position t??? HomeFramgent sang ScreenFragment
        number = bundle?.getInt("Key_position")!!

        // l???y d??? li???u songName t??? HomeFramgent sang ScreenFragment
        song_name = bundle?.getString("Key_song_name")!!

        // l???y d??? li???u imageURL t??? HomeFramgent sang ScreenFragment
        image_url = bundle?.getString("Key_image_URL")!!

        // l???y d??? li???u singerName t??? HomeFramgent sang ScreenFragment
        singer_name = bundle?.getString("Key_singer_name")!!

    }

    private fun doSomethingWithListUsers() {
        realtimeDatabaseHelper.getListUsersFromRealTimeDatabase(
            onSuccess = { musicList ->

                music = musicList.get(number!!)
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setDataSource(musicList!!.get(number!!).songURL)

                // hi???n th??? d??? li???u tr??n Song Name ra m??n h??nh ScreenFragment
                binding.tvSongName.text = song_name
                // hi???n th??? d??? li???u Singer Name ra m??n h??nh ScreenFragment
                binding.tvSingerName.text = singer_name
                // hi???n th??? d??? li???u Image ra m??n h??nh ScreenFragment
                Glide.with(binding.imgSong)
                    .load(image_url)
                    .into(binding.imgSong)

//Button Play

                // Code x??? l?? sau khi delay 3 gi??y
                binding.btnPlay.setOnClickListener {

//                    capNhatTimeBaiHat()
                    if (mediaPlayer!!.isPlaying) {
                        // N???u ??ang ph??t -> pause -> ?????i h??nh play
                        mediaPlayer!!.pause()
                        stopAnimation()
                        binding.btnPlay.setImageResource(R.drawable.ic_play)
                    } else {
                        displayTextView()

                        /**
                         *   (+) "mediaPlayer!!.currentPosition" : l?? v??? tr?? hi???n t???i c???a mediaPlayer trong qu?? tr??nh ph??t nh???c, t??nh b???ng mili gi??y (ms).
                         *   N???u gi?? tr??? n??y l???n h??n 0, t???c l?? mediaPlayer ???? ???????c ph??t ??t nh???t m???t ph???n c???a b??i h??t.
                         *
                         *   (+) "mediaPlayer!!.duration" : l?? th???i gian c???a b??i h??t, c??ng t??nh b???ng mili gi??y (ms).
                         *   N???u gi?? tr??? n??y kh??c v???i mediaPlayer!!.currentPosition, t???c l?? ch??a ph??t h???t b??i h??t, th?? mediaPlayer ??ang trong tr???ng th??i t???m d???ng.
                         *
                         *   (+) "mediaPlayer!!.currentPosition > 0 && mediaPlayer!!.duration != mediaPlayer!!.currentPosition".
                         *   N???u ??i???u ki???n ????ng, th?? mediaPlayer ??ang trong tr???ng th??i t???m d???ng v?? ch??ng ta s??? g???i mediaPlayer.start() ????? ti???p t???c ph??t nh???c t??? v??? tr?? hi???n t???i.
                         *
                         * */
                        if (mediaPlayer!!.currentPosition > 0 && mediaPlayer!!.duration != mediaPlayer!!.currentPosition) {
                            // ??ang t???m d???ng -> ti???p t???c ph??t
                            mediaPlayer!!.start()
                            startAnimation()
                        } else {
                            // ??ang d???ng ho???c ch??a b???t ?????u -> b???t ?????u ph??t
                            try {
                                mediaPlayer!!.prepare()
                                mediaPlayer!!.setOnPreparedListener(MediaPlayer.OnPreparedListener { mediaPlayer ->
                                    mediaPlayer!!.start()
                                    startAnimation()
                                })
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                        binding.btnPlay.setImageResource(R.drawable.ic_pause)
                        SetTimeTotal()
                        capNhatTimeBaiHat()
                    }
                }


// Button Repeat
                binding.btnRepeat.setOnClickListener {
                    // L???y gi?? tr??? hi???n t???i c???a t??nh n??ng l???p l???i
                    val isLooping = mediaPlayer!!.isLooping
                    // Thi???t l???p t??nh n??ng l???p l???i m???i
                    mediaPlayer!!.setLooping(!isLooping)
                    // Thay ?????i h??nh ???nh c???a n??t Repeat ????? ph???n ??nh tr???ng th??i m???i
                    if (isLooping) {
                        binding.btnRepeat.setImageResource(R.drawable.ic_repeat)
                    } else {
                        binding.btnRepeat.setImageResource(R.drawable.ic_repeat_one)
                    }
                }


// Button Next
                binding.btnNext.setOnClickListener {
                    number = (number + 1) % musicList.size // L???y v??? tr?? ph??t ti???p theo trong danh s??ch
                    music = musicList[number]
                    mediaPlayer!!.reset() // ?????t l???i MediaPlayer tr?????c khi ph??t b??i h??t m???i
                    mediaPlayer!!.setDataSource(music!!.songURL) // Set data source cho MediaPlayer
                    mediaPlayer!!.prepare() // Chu???n b??? MediaPlayer
                    mediaPlayer!!.start() // B???t ?????u ph??t nh???c
                    startAnimation()
                    // Update th??ng tin tr??n giao di???n
                    binding.tvSongName.text = music!!.songName
                    binding.tvSingerName.text = music!!.singerName
                    Glide.with(binding.imgSong)
                        .load(music!!.imageURL)
                        .into(binding.imgSong)
                    // Thay ?????i tr???ng th??i c???a n??t play
                    binding.btnPlay.setImageResource(R.drawable.ic_pause)

                    SetTimeTotal()
                    capNhatTimeBaiHat()
                }

// Button Previous
                binding.btnPrevious.setOnClickListener {
                    // L???y v??? tr?? ph??t ti???p theo trong danh s??ch
                    number = (number - 1) % musicList.size
                    music = musicList[number]
                    mediaPlayer!!.reset() // ?????t l???i MediaPlayer tr?????c khi ph??t b??i h??t m???i
                    mediaPlayer!!.setDataSource(music!!.songURL) // Set data source cho MediaPlayer
                    mediaPlayer!!.prepare() // Chu???n b??? MediaPlayer
                    mediaPlayer!!.start() // B???t ?????u ph??t nh???c
                    startAnimation()
                    // Update th??ng tin tr??n giao di???n
                    binding.tvSongName.text = music!!.songName
                    binding.tvSingerName.text = music!!.singerName
                    Glide.with(binding.imgSong)
                        .load(music!!.imageURL)
                        .into(binding.imgSong)
                    // Thay ?????i tr???ng th??i c???a n??t play
                    binding.btnPlay.setImageResource(R.drawable.ic_pause)

                    SetTimeTotal()
                    capNhatTimeBaiHat()
                }

                // b???t s??? ki???n tr??n SeekBak
                binding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {

                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {

                    }

                    // ch???m v??o k??o seekBar xong bu??ng ra th?? n?? s??? l???y gi?? tr??? seekBar cu???i c??ng khi bu??ng ra
                    override fun onStopTrackingTouch(seekBar: SeekBar) {
                        mediaPlayer!!.seekTo(seekBar.progress)
                    }
                })

                Log.d("EEE", "ScreenFragment arrayList : " + musicList!!.size)
            },
            onFailure = { exception ->
                // Handle error
            }
        )
    }

    private fun startAnimation() {
        val runnable: Runnable = object : Runnable {
            override fun run() {
                binding.imgSong.animate().rotationBy(360f).withEndAction(this).setDuration(20000)
                    .setInterpolator(LinearInterpolator()).start()
            }
        }
        binding.imgSong.animate().rotationBy(360f).withEndAction(runnable).setDuration(20000)
            .setInterpolator(LinearInterpolator()).start()
    }

    private fun stopAnimation() {
        binding.imgSong.animate().cancel()
    }

    private fun displayTextView() {
        // Hi???n th??? ??i tvStartTime v??  tvEndTime khi ??ang ??? m??n h??nh ch??? ScreenFragment
        binding.tvStartTime.visibility = View.VISIBLE
        binding.tvEndTime.visibility = View.VISIBLE
    }

    private fun capNhatTimeBaiHat() {
        realtimeDatabaseHelper.getListUsersFromRealTimeDatabase(
            onSuccess = { musicList ->
                val handler = Handler()
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        val dinhDangGio = SimpleDateFormat("mm:ss")
                        binding.tvStartTime.text = dinhDangGio.format(mediaPlayer?.currentPosition)

                        //update progress skSong
                        mediaPlayer?.currentPosition?.let {
                            binding.seekBar.progress = it
                        }

                        //Ki???m tra th???i gian b??i h??t -> n???u k???t th??c  -> next
                        mediaPlayer?.setOnCompletionListener {
                            number = (number + 1) % musicList.size // L???y v??? tr?? ph??t ti???p theo trong danh s??ch
                            music = musicList[number]
                            mediaPlayer!!.reset() // ?????t l???i MediaPlayer tr?????c khi ph??t b??i h??t m???i
                            mediaPlayer!!.setDataSource(music!!.songURL) // Set data source cho MediaPlayer
                            mediaPlayer!!.prepare() // Chu???n b??? MediaPlayer
                            mediaPlayer!!.start() // B???t ?????u ph??t nh???c
                            // Update th??ng tin tr??n giao di???n
                            binding.tvSongName.text = music!!.songName
                            binding.tvSingerName.text = music!!.singerName
                            Glide.with(binding.imgSong)
                                .load(music!!.imageURL)
                                .into(binding.imgSong)
                            // Thay ?????i tr???ng th??i c???a n??t play
                            binding.btnPlay.setImageResource(R.drawable.ic_pause)
                            SetTimeTotal()
                            capNhatTimeBaiHat()
                        }
                        handler.postDelayed(this, 500)
                    }
                }, 100)

                Log.d("RRR", "musicList : " + musicList.size)
            },
            onFailure = { exception ->
                // X??? l?? exception t???i ????y
            }
        )
    }

    private fun SetTimeTotal() {
        // h??m x??? l?? s??? ki???n ph??t v?? gi??y
        val dinhDangGio = SimpleDateFormat("mm:ss")
        binding.tvEndTime.setText(dinhDangGio.format(mediaPlayer!!.duration))

        // g??n SeeBak = t???ng th???i gian b??i h??t
        // t???c l?? g??n max c???a skSong = mediaPlayer.getDuration()
        binding.seekBar.max = mediaPlayer!!.duration
    }

}