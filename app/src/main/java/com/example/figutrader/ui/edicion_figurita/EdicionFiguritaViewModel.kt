package com.example.figutrader.ui.edicion_figurita

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.figutrader.model.FiguritaDataView


class EdicionFiguritaViewModel() : ViewModel() {
    var figuritasData = MutableLiveData<FiguritaDataView>()

    fun setFigurita(figurita: FiguritaDataView?) {
        figuritasData.value = figurita!!
    }
}