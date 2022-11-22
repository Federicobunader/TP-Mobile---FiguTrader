package com.example.figutrader.ui.album

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IAlbumService {
    @GET("album")
    fun getAlbum() : Call<List<FiguritaResult>>

    @GET("usuario/{userId}/album")
    fun getAlbumUsuario(@Path("userId") userId: String) : Call<List<FiguritaUsuarioResult>>

    @POST("usuario/{userId}/album")
    fun addFigurita(@Path("userId") userId: String, @Body figuData : FiguritaUsuarioData) : Call<List<FiguritaUsuarioResult>>
}

