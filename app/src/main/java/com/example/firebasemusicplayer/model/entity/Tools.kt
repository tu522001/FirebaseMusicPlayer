package com.example.firebasemusicplayer.model.entity

import com.example.firebasemusicplayer.R

data class Tools(val image: Int, val text: String)
{
    companion object {
        fun getMock(): MutableList<Tools> {
            return mutableListOf(
                Tools(R.drawable.b5,"Nghe nhạc"),
                Tools(R.drawable.b, "Phiên bản"),
                Tools(R.drawable.b1, "Trợ giúp"),
                Tools(R.drawable.b2, "Điều khoản"),
                Tools(R.drawable.b3, "Chính sách bảo mật"),
                Tools(R.drawable.b4, "Đăng xuất"),
            )
        }
    }
}