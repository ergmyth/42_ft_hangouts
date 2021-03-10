package ru.school21.eleonard.data.api.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
	@SerializedName("id")
	val userId: Int,
	@SerializedName("email")
	val email: String,
	@SerializedName("login")
	val login: String,
	@SerializedName("image_url")
	val imageUrl: String,
	@SerializedName("staff?")
	val isStaff: Boolean,
	@SerializedName("pool_month")
	val poolMonth: String,
	@SerializedName("pool_year")
	val poolYear: String,
	@SerializedName("wallet")
	val wallet: Int,
)