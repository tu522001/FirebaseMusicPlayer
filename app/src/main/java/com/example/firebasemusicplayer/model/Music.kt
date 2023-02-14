package com.example.firebasemusicplayer.model

import android.os.Parcelable

data class Music(
    var id: Int = -1,
    var songName: String = "",
    var singerName: String = "",
    var imageURL: String = "",
    var songURL: String = ""
)