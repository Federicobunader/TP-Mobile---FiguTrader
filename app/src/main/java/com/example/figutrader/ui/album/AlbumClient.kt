package com.example.figutrader.ui.album

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AlbumClient {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://10.0.2.2:4000/api/")
        .build()

    val service = retrofit.create(IAlbumService::class.java)
}