package com.example.musicant.mvvm.model.network.music_service

import com.example.musicant.mvvm.model.storage.room.entity.Album

interface NetworkMusicService {
    fun getFavouriteMusic(): List<Any>
    fun getAlbums(): List<Album>
    fun updateFavouriteMusic(data: Any)
}