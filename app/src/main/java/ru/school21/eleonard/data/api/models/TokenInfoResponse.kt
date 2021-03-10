package ru.school21.eleonard.data.api.models

import com.google.gson.annotations.SerializedName

data class TokenInfoResponse(
	@SerializedName("resource_owner_id")
	val ownerId: String,
	val scopes: List<String>,
	@SerializedName("expires_in_seconds")
	val expiresIn: Int,
	@SerializedName("application")
	val applicationInfo: ApplicationInfo
)