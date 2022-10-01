package com.example.figutrader.ui.registro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistroViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "FiguTrader"
    }
    val text: LiveData<String> = _text
}