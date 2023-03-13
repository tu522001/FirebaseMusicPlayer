package com.example.firebasemusicplayer.view.home

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.data.RealtimeDatabaseHelper
import com.example.firebasemusicplayer.databinding.FragmentScreenBinding
import com.example.firebasemusicplayer.model.Music
import kotlinx.android.synthetic.main.fragment_screen.*
import java.io.IOException
import java.text.SimpleDateFormat


class ScreenFragment : Fragment() {

    private var number: Int = 0
    private lateinit var song_name: String
    private lateinit var image_url: String
    private lateinit var singer_name: String
    private var isRepeating = false // Khai báo biến để lưu trạng thái đang phát nhạc
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

        return binding.root
    }

    private fun onClickPosition() {
        // Lấy position trong RecyclerView
        val bundle = arguments

        // lấy dữ liệu position từ HomeFramgent sang ScreenFragment
        number = bundle?.getInt("Key_position")!!

        // lấy dữ liệu songName từ HomeFramgent sang ScreenFragment
        song_name = bundle?.getString("Key_song_name")!!

        // lấy dữ liệu imageURL từ HomeFramgent sang ScreenFragment
        image_url = bundle?.getString("Key_image_URL")!!

        // lấy dữ liệu singerName từ HomeFramgent sang ScreenFragment
        singer_name = bundle?.getString("Key_singer_name")!!

    }

    private fun doSomethingWithListUsers() {
        realtimeDatabaseHelper.getListUsersFromRealTimeDatabase(
            onSuccess = { musicList ->

                music = musicList.get(number!!)
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setDataSource(musicList!!.get(number!!).songURL)

                // hiển thị dữ liệu trên Song Name ra màn hình ScreenFragment
                binding.tvSongName.text = song_name
                // hiển thị dữ liệu Singer Name ra màn hình ScreenFragment
                binding.tvSingerName.text = singer_name
                // hiển thị dữ liệu Image ra màn hình ScreenFragment
                Glide.with(binding.imgSong)
                    .load(image_url)
                    .into(binding.imgSong)

//Button Play

                // Code xử lý sau khi delay 3 giây
                binding.btnPlay.setOnClickListener {

//                    capNhatTimeBaiHat()
                    if (mediaPlayer!!.isPlaying) {
                        // Nếu đang phát -> pause -> đổi hình play
                        mediaPlayer!!.pause()
                        binding.btnPlay.setImageResource(R.drawable.ic_play)
                    } else {
                        displayTextView()

                        /**
                         *   (+) "mediaPlayer!!.currentPosition" : là vị trí hiện tại của mediaPlayer trong quá trình phát nhạc, tính bằng mili giây (ms).
                         *   Nếu giá trị này lớn hơn 0, tức là mediaPlayer đã được phát ít nhất một phần của bài hát.
                         *
                         *   (+) "mediaPlayer!!.duration" : là thời gian của bài hát, cũng tính bằng mili giây (ms).
                         *   Nếu giá trị này khác với mediaPlayer!!.currentPosition, tức là chưa phát hết bài hát, thì mediaPlayer đang trong trạng thái tạm dừng.
                         *
                         *   (+) "mediaPlayer!!.currentPosition > 0 && mediaPlayer!!.duration != mediaPlayer!!.currentPosition".
                         *   Nếu điều kiện đúng, thì mediaPlayer đang trong trạng thái tạm dừng và chúng ta sẽ gọi mediaPlayer.start() để tiếp tục phát nhạc từ vị trí hiện tại.
                         *
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
                        SetTimeTotal()
                        capNhatTimeBaiHat()
                    }
                }


// Button Repeat
                binding.btnRepeat.setOnClickListener {
                    // Lấy giá trị hiện tại của tính năng lặp lại
                    val isLooping = mediaPlayer!!.isLooping
                    // Thiết lập tính năng lặp lại mới
                    mediaPlayer!!.setLooping(!isLooping)
                    // Thay đổi hình ảnh của nút Repeat để phản ánh trạng thái mới
                    if (isLooping) {
                        binding.btnRepeat.setImageResource(R.drawable.ic_repeat)
                    } else {
                        binding.btnRepeat.setImageResource(R.drawable.ic_repeat_one)
                    }
                }


// Button Next
                binding.btnNext.setOnClickListener {
                    number = (number + 1) % musicList.size // Lấy vị trí phát tiếp theo trong danh sách
                    music = musicList[number]
                    mediaPlayer!!.reset() // Đặt lại MediaPlayer trước khi phát bài hát mới
                    mediaPlayer!!.setDataSource(music!!.songURL) // Set data source cho MediaPlayer
                    mediaPlayer!!.prepare() // Chuẩn bị MediaPlayer
                    mediaPlayer!!.start() // Bắt đầu phát nhạc
                    // Update thông tin trên giao diện
                    binding.tvSongName.text = music!!.songName
                    binding.tvSingerName.text = music!!.singerName
                    Glide.with(binding.imgSong)
                        .load(music!!.imageURL)
                        .into(binding.imgSong)
                    // Thay đổi trạng thái của nút play
                    binding.btnPlay.setImageResource(R.drawable.ic_pause)

                    SetTimeTotal()
                    capNhatTimeBaiHat()
                }

// Button Previous
                binding.btnPrevious.setOnClickListener {
                    number =
                        (number - 1) % musicList.size // Lấy vị trí phát tiếp theo trong danh sách
                    music = musicList[number]
                    mediaPlayer!!.reset() // Đặt lại MediaPlayer trước khi phát bài hát mới
                    mediaPlayer!!.setDataSource(music!!.songURL) // Set data source cho MediaPlayer
                    mediaPlayer!!.prepare() // Chuẩn bị MediaPlayer
                    mediaPlayer!!.start() // Bắt đầu phát nhạc
                    // Update thông tin trên giao diện
                    binding.tvSongName.text = music!!.songName
                    binding.tvSingerName.text = music!!.singerName
                    Glide.with(binding.imgSong)
                        .load(music!!.imageURL)
                        .into(binding.imgSong)
                    // Thay đổi trạng thái của nút play
                    binding.btnPlay.setImageResource(R.drawable.ic_pause)

                    SetTimeTotal()
                    capNhatTimeBaiHat()
                }

                // bắt sự kiện trên SeekBak
                binding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {

                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {

                    }

                    // chạm vào kéo seekBar xong buông ra thì nó sẽ lấy giá trị seekBar cuối cùng khi buông ra
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

    private fun displayTextView() {
        // Hiển thị đi tvStartTime và  tvEndTime khi đang ở màn hình chờ ScreenFragment
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

                        //Kiểm tra thời gian bài hát -> nếu kết thúc  -> next
                        mediaPlayer?.setOnCompletionListener {
                            number = (number + 1) % musicList.size // Lấy vị trí phát tiếp theo trong danh sách
                            music = musicList[number]
                            mediaPlayer!!.reset() // Đặt lại MediaPlayer trước khi phát bài hát mới
                            mediaPlayer!!.setDataSource(music!!.songURL) // Set data source cho MediaPlayer
                            mediaPlayer!!.prepare() // Chuẩn bị MediaPlayer
                            mediaPlayer!!.start() // Bắt đầu phát nhạc
                            // Update thông tin trên giao diện
                            binding.tvSongName.text = music!!.songName
                            binding.tvSingerName.text = music!!.singerName
                            Glide.with(binding.imgSong)
                                .load(music!!.imageURL)
                                .into(binding.imgSong)
                            // Thay đổi trạng thái của nút play
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
                // Xử lý exception tại đây
            }
        )
    }

    private fun SetTimeTotal() {
        // hàm xử lý sự kiện phút và giây
        val dinhDangGio = SimpleDateFormat("mm:ss")
        binding.tvEndTime.setText(dinhDangGio.format(mediaPlayer!!.duration))

        // gán SeeBak = tổng thời gian bài hát
        // tức là gán max của skSong = mediaPlayer.getDuration()
        binding.seekBar.max = mediaPlayer!!.duration
    }

}