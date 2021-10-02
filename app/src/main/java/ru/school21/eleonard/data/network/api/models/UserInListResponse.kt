package ru.school21.eleonard.data.network.api.models

import com.google.gson.annotations.SerializedName

data class UserInListResponse(
	@SerializedName("id")
	val userId: Int,
	@SerializedName("login")
	val login: String,
)