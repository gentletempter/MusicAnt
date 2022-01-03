package com.example.musicant.mvvm.model.storage.room

interface AlbumDatabase {
    fun getAlbumDao(): AlbumDao
}