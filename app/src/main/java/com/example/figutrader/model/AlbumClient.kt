package com.example.figutrader.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AlbumClient {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://figutrader-api.onrender.com/api/")
        .build()

    val service = retrofit.create(IAlbumService::class.java)
}