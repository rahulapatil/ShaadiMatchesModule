package com.example.shaadimatchesmodule.interactions

import com.example.shaadimatchesmodule.database.ShadiMatchEntity

//TODO:INTERFACE TO GET CLICKS IN ACTIVITY FROM RECYCLERVIEW
interface ItemInteractor {
    fun onDeclineImageClick(shadiMatchPojo: ShadiMatchEntity,position:Int)
    fun onAcceptImageClick(shadiMatchPojo: ShadiMatchEntity,position:Int)
}