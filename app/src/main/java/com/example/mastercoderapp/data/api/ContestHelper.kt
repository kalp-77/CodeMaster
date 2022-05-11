package com.example.mastercoderapp.data.api
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ContestHelper {
    private const val BASE_URL_1 = "https://kontests.net"
    private const val BASE_URL_2 = "https://contest-details.herokuapp.com/api/"
    private const val BASE_URL_3 = "https://cping-api2.herokuapp.com"

    //codeforces
    private const val BASE_URL_4 = "https://codeforces.com/api/"

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    fun getInstance1() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_1)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getInstance2() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_2)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getInstance3() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_3)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getInstance4() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_4)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    fun getInstance5() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_4)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}