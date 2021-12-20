package com.example.musicant.mvvm.model.network

import com.example.musicant.mvvm.model.entity.Album

interface NetworkMusicService {
    fun getFavouriteMusic(): List<Any>
    fun getAlbums(): List<Album>
    fun updateFavouriteMusic(data: Any)
}