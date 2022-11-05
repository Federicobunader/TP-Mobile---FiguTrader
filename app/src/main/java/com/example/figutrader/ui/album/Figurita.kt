package com.example.figutrader.ui.album

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FiguritaService {
    @GET("api/usuario/{userId}/figuritas")
    fun getFiguritas(@Path("userId") userId: String) : Call<List<FiguritaResponse>>
}

class FiguritaResponse() {
    @SerializedName("descripcion")
    var descripcion : String? = null
    @SerializedName("cantidad")
    var cantidad : Int? = null
    @SerializedName("categoria")
    var categoria : String? = null
    @SerializedName("id")
    var id : Int? = null
    @SerializedName("usuarioId")
    var usuarioId : Int? = null
}

//class Figurita() {
//
//}

data class Figurita(val descripcion: String, val cantidad: Int, val pais: String, val idFigurita: Int, val idUsuario: String)



//https://635ef291ed25a0b5fe4fbaeb.mockapi.io/api/usuario/3/figuritas