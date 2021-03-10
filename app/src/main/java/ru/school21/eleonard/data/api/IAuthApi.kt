package ru.school21.eleonard.data.api

import retrofit2.Call
import retrofit2.http.*
import ru.school21.eleonard.data.TokenRepository
import ru.school21.eleonard.data.api.models.AccessTokenRequest
import ru.school21.eleonard.data.api.models.AccessTokenResponse
import ru.school21.eleonard.data.api.models.TokenInfoResponse

interface IAuthApi {
	@POST("/oauth/token")
	fun getAccessToken(@Body data: AccessTokenRequest): Call<AccessTokenResponse>

	@GET("/oauth/token/info")
	fun getTokenInfo(@Header("Authorization") Authorization: String = "Bearer " + TokenRepository.accessToken): Call<TokenInfoResponse>
}