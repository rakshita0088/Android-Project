package com.example.autovault.network

//class RetrofitClient {
//}
//package com.example.autovault.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.jsonbin.io/") // e.g., "https://api.jsonbin.io/v3/"
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(SOSApi::class.java)
}
