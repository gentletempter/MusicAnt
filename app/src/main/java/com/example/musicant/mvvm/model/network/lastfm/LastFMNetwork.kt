package com.example.musicant.mvvm.model.network.lastfm

interface LastFMNetwork {
    fun getArtistsService(): ArtistsService
}