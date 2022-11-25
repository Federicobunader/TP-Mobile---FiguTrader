package com.example.figutrader.ui.edicion_figurita

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.figutrader.ui.album.FiguritaDataView


class EdicionFiguritaViewModel() : ViewModel() {
    var figuritasData = MutableLiveData<FiguritaDataView>()

    fun setFigurita(figurita: FiguritaDataView?) {
        figuritasData.value = figurita!!
    }
}