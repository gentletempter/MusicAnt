package com.example.musicant.mvvm.model.network.artist_model

import com.google.gson.annotations.SerializedName

data class Artists(
    @SerializedName("artist") val artist: List<Artist>,
    @SerializedName("@attr") val attr: Attr
)