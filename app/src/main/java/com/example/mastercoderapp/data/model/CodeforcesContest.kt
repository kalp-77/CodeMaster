package com.example.mastercoderapp.data.model


import com.google.gson.annotations.SerializedName

data class CodeforcesContest(
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("status")
    val status: String
)