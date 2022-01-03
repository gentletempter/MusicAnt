package com.example.musicant.mvvm.model.network.lastfm

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LastFMNetworkImpl private constructor() : LastFMNetwork {

    private lateinit var artistsService: ArtistsService

    /**
     * Create a Singleton
     */
    companion object {
        private var instance: LastFMNetworkImpl? = null

        fun getInstance(): LastFMNetworkImpl {
            if (instance == null) {
                instance = LastFMNetworkImpl()
            }
            return instance!!
        }
    }

    init {
        initService()
    }

    private fun initService() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        artistsService = retrofit.create(ArtistsService::class.java)
    }

    override fun getArtistsService(): ArtistsService = artistsService
}