package com.example.musicant.mvvm.ui.music

import androidx.lifecycle.*
import com.example.musicant.mvvm.model.entity.Album
import com.example.musicant.mvvm.model.network.NetworkMusicService
import com.example.musicant.mvvm.model.network.NetworkMusicServiceImpl

class ListViewModel : ViewModel(), LifecycleEventObserver {

    val albumsLiveData = MutableLiveData<List<Album>>()

    private val musicModel: NetworkMusicService = NetworkMusicServiceImpl()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                println("ON_CREATE")
                getAlbums()
            }
        }
    }

    private fun getAlbums() {
        val albums = musicModel.getAlbums()
        albumsLiveData.value = albums
    }

}