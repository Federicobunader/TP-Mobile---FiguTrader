package com.example.figutrader.ui.menu_principal

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuPrincipalViewModel() : ViewModel() {

    var userNameData = MutableLiveData<String>()
    var userIdData = MutableLiveData<String?>()


    fun setUsername(user: String?) {
        userNameData.value = "Album de " + user!!
    }

    fun setUserId(userId : String?) {
        userIdData.value = userId!!
    }
}