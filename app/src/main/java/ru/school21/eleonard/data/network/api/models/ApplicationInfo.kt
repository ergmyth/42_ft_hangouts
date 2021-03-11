package ru.school21.eleonard.data.network.api.models

import com.google.gson.annotations.SerializedName

data class ApplicationInfo(
	val uid: String,
	@SerializedName("created_at")
	val createdAt: Long,
)