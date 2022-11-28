package com.example.figutrader.model

import com.google.gson.annotations.SerializedName

data class FiguritaUsuarioData(
    @SerializedName("cantidad")
    val cantidad : Int,
    @SerializedName("figuId")
    val figuId : Int,
)