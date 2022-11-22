package com.example.figutrader.ui.album

import com.google.gson.annotations.SerializedName

data class FiguritaResult (
    @SerializedName("descripcion")
    val descripcion : String,
    @SerializedName("categoria")
    val categoria : String,
    @SerializedName("figuId")
    val figuId : Int
)