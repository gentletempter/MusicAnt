package com.example.musicant.mvvm.model.network.artist_model

import com.google.gson.annotations.SerializedName

data class ArtistsResponse(
    @SerializedName("artists") val artists: Artists
)