package ru.school21.eleonard.data.network

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.school21.eleonard.data.network.api.IAuthApi
import ru.school21.eleonard.data.network.api.models.AccessTokenRequest
import ru.school21.eleonard.data.network.api.models.AccessTokenResponse
import ru.school21.eleonard.Constants
import java.io.IOException

class ApiAuthenticator : Authenticator {
	@Throws(IOException::class)
	override fun authenticate(route: Route?, response: Response): Request? {
		val originalRequest = response.request.newBuilder().header("Authorization", TokenRepository.accessToken).build()
		synchronized(this) {
			return when (response.code) {
				Constants.HTTP_UNAUTHORIZED -> {
					val newToken = getNewToken()
					if (newToken?.code() == Constants.HTTP_SUCCESSFUL) {
						updateTokens(newToken)
						originalRequest.newBuilder().header("Authorization", TokenRepository.accessToken).build()
					} else
						null
				}
				else -> null
			}
		}
	}

	private fun updateTokens(newToken: retrofit2.Response<AccessTokenResponse>) {
		newToken.body()?.let {
			TokenRepository.saveToken(it.accessToken)
		}
	}

	private fun getNewToken(): retrofit2.Response<AccessTokenResponse>? {
		return runBlocking {
			val accessTokenRequest = AccessTokenRequest(
				uid = Constants.UID,
				secretId = Constants.SECRET_ID,
				grantType = Constants.GRANT_TYPE,
			)
			try {
				NetworkHolder.authenticatorRetrofitClient.create(IAuthApi::class.java).getAccessToken(accessTokenRequest).execute()
			} catch (e: IOException) {
				null
			}
		}
	}
}