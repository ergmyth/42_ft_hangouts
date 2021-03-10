package ru.school21.eleonard.data

import ru.school21.eleonard.BaseApp
import ru.school21.eleonard.helpers.Constants

object TokenRepository {
	var accessToken: String = ""
	var accessTokenExpireTime: Long = 0L

	fun loadTokenFromShared(_accessToken: String, expiresIn: Long) {
		setTokenToLocal(_accessToken, expiresIn)
	}

	fun saveToken(_accessToken: String, expiresIn: Long) {
		setTokenToLocal(_accessToken, expiresIn)
		saveTokenToSharedPref(_accessToken, expiresIn)
	}

	private fun saveTokenToSharedPref(_accessToken: String, expiresIn: Long) {
		BaseApp.getSharedPref().edit().putString(Constants.ACCESS_TOKEN, _accessToken).apply()
		BaseApp.getSharedPref().edit().putLong(Constants.ACCESS_TOKEN_LIVE_TIME, expiresIn).apply()
	}

	fun deleteToken() {
		accessToken = ""
		accessTokenExpireTime = 0L
		BaseApp.getSharedPref().edit().putString(Constants.ACCESS_TOKEN, "").apply()
		BaseApp.getSharedPref().edit().putLong(Constants.ACCESS_TOKEN_LIVE_TIME, 0L).apply()
	}

	private fun setTokenToLocal(_accessToken: String, expiresIn: Long) {
		accessToken = _accessToken
		accessTokenExpireTime = expiresIn
	}

	private fun getTokenExpireTime(): Long {
		return 0L
	}
}