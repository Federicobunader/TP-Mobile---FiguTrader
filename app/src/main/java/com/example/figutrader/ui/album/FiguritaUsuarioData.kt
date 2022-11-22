package com.example.figutrader.ui.album

import com.google.gson.annotations.SerializedName

data class FiguritaUsuarioData(
    @SerializedName("cantidad")
    val cantidad : Int,
    @SerializedName("figuId")
    val figuId : Int,
)