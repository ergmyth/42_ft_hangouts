package ru.school21.eleonard.data.api.models

import com.google.gson.annotations.SerializedName

data class AccessTokenRequest(
	@SerializedName("client_id")
	val uid: String,
	@SerializedName("client_secret")
	val secretId: String,
	@SerializedName("grant_type")
	val grantType: String,
)