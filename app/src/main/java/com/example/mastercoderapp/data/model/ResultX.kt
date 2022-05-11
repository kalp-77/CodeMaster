package com.example.mastercoderapp.data.model


import com.google.gson.annotations.SerializedName

data class ResultX(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("contribution")
    val contribution: Int,
    @SerializedName("friendOfCount")
    val friendOfCount: Int,
    @SerializedName("handle")
    val handle: String,
    @SerializedName("lastOnlineTimeSeconds")
    val lastOnlineTimeSeconds: Int,
    @SerializedName("maxRank")
    val maxRank: String,
    @SerializedName("maxRating")
    val maxRating: Int,
    @SerializedName("rank")
    val rank: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("registrationTimeSeconds")
    val registrationTimeSeconds: Int,
    @SerializedName("titlePhoto")
    val titlePhoto: String
)