package com.example.mastercoderapp.data.model


import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("contests")
    val contests: List<Int>,
    @SerializedName("country rank")
    val countryRank: Int,
    @SerializedName("div")
    val div: String,
    @SerializedName("global rank")
    val globalRank: Int,
    @SerializedName("max rating")
    val maxRating: String,
    @SerializedName("platform")
    val platform: String,
    @SerializedName("problem solved")
    val problemSolved: Int,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("stars")
    val stars: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("username")
    val username: String
)