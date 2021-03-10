package ru.school21.eleonard.data.api.models

import com.google.gson.annotations.SerializedName

data class AccessTokenResponse(
	@SerializedName("access_token")
	val accessToken: String,
	@SerializedName("token_type")
	val tokenType: String,
	@SerializedName("expires_in")
	val expiresIn: Long,
	val scope: String,
	@SerializedName("created_at")
	val createdAt: Long,
)