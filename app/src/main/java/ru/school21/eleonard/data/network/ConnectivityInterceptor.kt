package ru.school21.eleonard.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.school21.eleonard.BaseApp
import ru.school21.eleonard.R
import ru.school21.eleonard.data.network.helpers.NetworkUtils
import java.io.IOException

class ConnectivityInterceptor : Interceptor {
	@Throws(IOException::class)
	override fun intercept(chain: Interceptor.Chain): Response {
		return if (NetworkUtils().hasNetwork())
			chain.proceed(chain.request())
		else {
			throw object : IOException() {
				override val message: String
					get() = BaseApp.getInstance().resources.getString(R.string.network_connection_error)
			}
		}
	}
}
