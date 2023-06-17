package com.example.firebasemusicplayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasemusicplayer.model.data.RealtimeDatabaseHelper
import com.example.firebasemusicplayer.model.entity.Music
import com.example.firebasemusicplayer.model.entity.Photo
import com.example.firebasemusicplayer.model.entity.Singer


class HomeViewModel : ViewModel() {
    private val _listSong = MutableLiveData<List<Music>>()
    val listSong: LiveData<List<Music>> = _listSong

    private val _listPhoto = MutableLiveData<List<Photo>>()
    val listPhoto: LiveData<List<Photo>> = _listPhoto

    private val _listSinger = MutableLiveData<List<Singer>>()
    val listSinger: LiveData<List<Singer>> = _listSinger
    fun fetchAllSongsFromFirebase() {
        RealtimeDatabaseHelper.getAllSongsFromFirebase(onSuccess = { musicList ->
            _listSong.postValue(musicList)
        }, onFailure = { })
    }

    fun fetchAllImagesFromFirebase() {
        RealtimeDatabaseHelper.getAllImagesFromFirebase(onSuccess = { photoList ->
            _listPhoto.postValue(photoList)
        }, onFailure = { })
    }

    fun fetchAllSingersFromFirebase() {
        RealtimeDatabaseHelper.getAllSingersFromFirebase(onSuccess = {
            listSinger -> _listSinger.postValue(listSinger)
        }, onFailure = {})
    }

}