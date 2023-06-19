package com.example.firebasemusicplayer.view.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentSingerInformationBinding

class SingerInformationFragment : Fragment() {

    private lateinit var imageURL : String
    private lateinit var singerName : String
    private lateinit var data : String
    private lateinit var height : String
    private lateinit var placeOfBirth : String
    private lateinit var yearOfOperation : String
    private lateinit var sex : String

    private lateinit var binding: FragmentSingerInformationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSingerInformationBinding.inflate(layoutInflater)

        onClickSingerInformation()
        initView()
        return binding.root
    }

    private fun initView() {
        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
        Glide.with(binding.imgSong.context).load(imageURL).into(binding.imgSong)
        binding.tvData.text = "- Ngày sinh : "+data
        binding.tvHeight.text = "- Chiều cao : "+height
        binding.tvPlaceOfBirth.text = "- Quê quán : "+placeOfBirth
        binding.tvYearOfOperation.text = "- Hoạt động từ năm : "+yearOfOperation
        binding.tvSex.text = "- Giới tính : "+sex
        binding.tvSingerName.text = singerName
    }

    private fun onClickSingerInformation() {
        // Lấy position trong RecyclerView
        val bundle = arguments

        imageURL = bundle?.getString("Key_image_URL")!!
        singerName = bundle?.getString("Key_singer_name")!!
        data = bundle?.getString("Key_data")!!
        height = bundle?.getString("Key_height")!!
        placeOfBirth = bundle?.getString("Key_placeOfBirth")!!
        yearOfOperation = bundle?.getString("Key_yearOfOperation")!!
        sex = bundle?.getString("Key_sex")!!

    }
}