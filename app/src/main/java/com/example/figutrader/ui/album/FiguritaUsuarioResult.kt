package com.example.figutrader.ui.album

import com.google.gson.annotations.SerializedName

data class FiguritaUsuarioResult(
    @SerializedName("id")
    val id : Int,
    @SerializedName("cantidad")
    val cantidad : Int,
    @SerializedName("figuId")
    val figuId : Int,
    @SerializedName("usuarioId")
    val usuarioId : String
)
