package com.example.firebasemusicplayer.model.entity

import com.example.firebasemusicplayer.R

data class ToolsAdmin(val image: Int, val text: String){

    companion object{
        fun getMock() : MutableList<ToolsAdmin>{
            return mutableListOf(
                ToolsAdmin(R.drawable.b5,"Nghe nhạc"),
                ToolsAdmin(R.drawable.xls1, "Xuất file Excel"),
                ToolsAdmin(R.drawable.plus, "Thêm bài hát"),
                ToolsAdmin(R.drawable.b, "Phiên bản"),
                ToolsAdmin(R.drawable.b1, "Trợ giúp"),
                ToolsAdmin(R.drawable.b2, "Điều khoản"),
                ToolsAdmin(R.drawable.b3, "Chính sách bảo mật"),
                ToolsAdmin(R.drawable.b4, "Đăng xuất"),
            )
        }
    }
}