package com.musicoolapp.musicool.red

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Red(private val connectivityManager: ConnectivityManager){
    fun performAction(action : () -> Unit){
        if(hayInternet()){
            action()
        }
    }

    private fun hayInternet(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?:return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)


    }
}