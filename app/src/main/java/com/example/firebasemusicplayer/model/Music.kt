package com.example.firebasemusicplayer.model

data class Music(
    var id: Int = 0,
    var songName: String = "",
    var singerName: String = "",
    var imageURL: String = "",
    var songURL: String = ""
)