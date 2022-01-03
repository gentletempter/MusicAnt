package com.example.musicant.mvvm.model.network.lastfm

import com.example.musicant.mvvm.model.network.artist_model.ArtistsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * For parse: https://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&api_key=a35699f435445486aec22d7a19e652bf&format=json
 **/
interface ArtistsService {

    companion object {
        private const val BASE_PATH = "2.0"
        private const val KEY_METHOD = "chart.gettopartists"
        private const val KEY_API_KEY = "a35699f435445486aec22d7a19e652bf"
        private const val KEY_FORMAT = "json"
    }

    @GET("$BASE_PATH/")
    suspend fun getTopArtists(
        @Query("method") method: String = KEY_METHOD,
        @Query("api_key") apiKey: String = KEY_API_KEY,
        @Query("format") format: String = KEY_FORMAT,
    ): ArtistsResponse
}