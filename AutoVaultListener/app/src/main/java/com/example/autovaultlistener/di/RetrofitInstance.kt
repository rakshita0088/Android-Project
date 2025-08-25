package com.example.autovaultlistener.di

import com.example.autovaultlistener.data.remote.JSONBinApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: JSONBinApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.jsonbin.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JSONBinApi::class.java)
    }
}