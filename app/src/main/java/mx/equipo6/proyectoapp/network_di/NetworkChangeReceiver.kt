package mx.equipo6.proyectoapp.network_di

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkChangeReceiver : BroadcastReceiver() {

    object NetworkChangeReceiver{

        fun isNetworkConnected(context: Context?): Boolean {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }

    }

    override fun onReceive(context: Context, intent: Intent) {
        /*
                if (NetworkChangeReceiver.isNetworkConnected(context)) {
                    Toast
                        .makeText(context, "Internet Connected", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("NetworkChangeReceiver", "Internet Connected")
                } else {
                    Toast
                        .makeText(context, "Internet Disconnected", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("NetworkChangeReceiver", "Internet Disconnected")
                }
        */
    }
}