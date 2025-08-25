package com.example.autovaultlistener.di

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.autovault.data.car_api.remote.JSONBinApi

object RetrofitInstance {
    val api: JSONBinApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.jsonbin.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JSONBinApi::class.java)
    }
}