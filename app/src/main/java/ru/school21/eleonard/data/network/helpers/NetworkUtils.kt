package ru.school21.eleonard.data.network.helpers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import ru.school21.eleonard.BaseApp

class NetworkUtils {
	fun hasNetwork(): Boolean {
		val connectivityManager = BaseApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
		return activeNetwork?.isConnectedOrConnecting == true
	}
}