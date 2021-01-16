package com.example.shaadimatchesmodule.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import com.example.shaadimatchesmodule.R

object AppAndroidUtils {

    private fun isNetWorkAvailable(showMessage: Boolean): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val manager = MyApp.appContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
            val isOnline =
                capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            if (isOnline) {
                return true
            } else if (showMessage) {
                showLongToastMessage(MyApp.appContext?.getString(R.string.hint_network_error))
            }
        } else {
            val connMgr = MyApp.appContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                return true
            } else if (showMessage) {
                showLongToastMessage(MyApp.appContext?.getString(R.string.hint_network_error))
            }
        }
        return false
    }

    fun isNetWorkAvailable(): Boolean {
        return isNetWorkAvailable(true)
    }

    fun showLongToastMessage(message: String?) {
        if (!message.isNullOrEmpty()) {
            Toast.makeText(MyApp.appContext, message, Toast.LENGTH_LONG).show()
        }
    }

    fun showShortToastMessage(message: String?) {
        if (!message.isNullOrEmpty()) {
            Toast.makeText(MyApp.appContext, message, Toast.LENGTH_SHORT).show()
        }
    }
}