package com.example.shaadimatchesmodule.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shaadimatchesmodule.database.ShadiMatchEntity

//TODO:ViewModel
class ShaadiViewModel : ViewModel() {

    var liveDataLogin: LiveData<ShadiMatchEntity>? = null

    fun insertData(context: Context, name: String, email: String,country:String,state:String,city:String,imageUrl:String,status:String) {
        ShadiRepository.insertData(context, name, email,country,state,city,imageUrl,status)
    }

    fun getShadiMatchDetails(context: Context) : LiveData<ShadiMatchEntity>? {
        liveDataLogin = ShadiRepository.getLoginDetails(context)
        return liveDataLogin
    }

}