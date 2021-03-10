package ru.school21.eleonard.data

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.runBlocking
import ru.school21.eleonard.BaseApp
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.school21.eleonard.data.api.IAuthApi
import ru.school21.eleonard.data.api.models.AccessTokenRequest
import ru.school21.eleonard.data.api.models.AccessTokenResponse
import ru.school21.eleonard.data.api.models.TokenInfoResponse
import ru.school21.eleonard.data.db.DbUtils
import ru.school21.eleonard.helpers.Constants
import java.io.IOException

class ApiAuthenticator : Authenticator {
	@EntryPoint
	@InstallIn(ApplicationComponent::class)
	interface ApiAuthenticatorEntryPoint {
		fun provideContext(): Context
		fun provideDbUtils(): DbUtils
	}

	var context: Context
	var dbUtils: DbUtils

	init {
		val appContext = BaseApp.getInstance().applicationContext ?: throw IllegalStateException()
		val hiltEntryPoint =
			EntryPointAccessors.fromApplication(appContext, ApiAuthenticatorEntryPoint::class.java)

		dbUtils = hiltEntryPoint.provideDbUtils()
		context = hiltEntryPoint.provideContext()
	}

	@Throws(IOException::class)
	override fun authenticate(route: Route?, response: Response): Request? {
		val originalRequest = response.request.newBuilder().header("Authorization", "Bearer " + TokenRepository.accessToken).build()
		synchronized(this) {
			return when (response.code) {
				401 -> {
					val tokenInfo = getTokenInfo()
					if (tokenInfo?.body() == null || tokenInfo.body()?.expiresIn == 0) {
						val newToken = getNewToken()
						if (newToken?.code() == 200) {
							updateTokens(newToken)
							originalRequest.newBuilder().header("Authorization", "Bearer " + TokenRepository.accessToken).build()
						} else
							null
					} else {
						resetDataAndLogout()
						null
					}
				}
				else -> null
			}
		}
	}

	private fun updateTokens(newToken: retrofit2.Response<AccessTokenResponse>) {
		newToken.body()?.let {
			TokenRepository.saveToken(it.accessToken, it.expiresIn)
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

	private fun getTokenInfo(): retrofit2.Response<TokenInfoResponse>? {
		return runBlocking {
			try {
				NetworkHolder.authenticatorRetrofitClient.create(IAuthApi::class.java).getTokenInfo().execute()
			} catch (e: IOException) {
				null
			}
		}
	}

	private fun resetDataAndLogout() {
		dbUtils.purgeBase()
		TokenRepository.deleteToken()
		NetworkHolder.httpClient.dispatcher.cancelAll()
	}
}