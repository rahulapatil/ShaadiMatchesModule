package com.example.shaadimatchesmodule.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext!!
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}