package com.example.mastercoderapp.data.api

import com.example.mastercoderapp.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ContestApi {

    @GET("/api/v1/{platform_name}")
    suspend fun getContest1(@Path("platform_name") platform_name:String): Response<ContestDetails>

    // my api
    @GET("{platform_name}/{user_name}")
    suspend fun getContest2(@Path("platform_name") platform_name:String, @Path("user_name") user_name: String): Response<UserDetails>

    // cping api
    @GET("/api/{platform_name}/{user_name}")
    suspend fun getContest3(@Path("platform_name") platform_name:String, @Path("user_name") user_name: String): Response<LeetcodeDetails>

    // codeforces api 4
    @GET("user.info?")
    suspend fun getContest4(@Query("handles") handles: String): Response<CodeforcesDetails>

    // rating 5
    @GET("user.rating?")
    suspend fun getContest5(@Query("handle") handle: String): Response<CodeforcesContest>


}