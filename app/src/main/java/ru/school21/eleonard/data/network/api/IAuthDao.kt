package ru.school21.eleonard.data.network.api

import retrofit2.Call
import retrofit2.http.*
import ru.school21.eleonard.data.network.api.models.AccessTokenRequest
import ru.school21.eleonard.data.network.api.models.AccessTokenResponse

interface IAuthDao {
	@POST("/oauth/token")
	fun getAccessToken(@Body data: AccessTokenRequest): Call<AccessTokenResponse>
}