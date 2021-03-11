package ru.school21.eleonard.data.network

import ru.school21.eleonard.BaseApp
import ru.school21.eleonard.Constants

object TokenRepository {
	var accessToken: String = ""

	fun loadTokenFromShared(_accessToken: String) {
		setTokenToLocal(_accessToken)
	}

	fun saveToken(_accessToken: String) {
		setTokenToLocal(_accessToken)
		saveTokenToSharedPref(_accessToken)
	}

	private fun saveTokenToSharedPref(_accessToken: String) {
		BaseApp.getSharedPref().edit().putString(Constants.SP_ACCESS_TOKEN, _accessToken).apply()
	}

	fun deleteToken() {
		accessToken = ""
		BaseApp.getSharedPref().edit().putString(Constants.SP_ACCESS_TOKEN, "").apply()
	}

	private fun setTokenToLocal(_accessToken: String) {
		accessToken = Constants.TOKEN_PREFIX + _accessToken
	}
}