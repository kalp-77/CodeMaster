package com.example.mastercoderapp.data.model


import com.google.gson.annotations.SerializedName

data class CodeforcesDetails(
    @SerializedName("result")
    val result: List<ResultX>,
    @SerializedName("status")
    val status: String
)