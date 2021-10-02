package ru.school21.eleonard.data.network.api

import retrofit2.http.*
import ru.school21.eleonard.data.network.TokenRepository
import ru.school21.eleonard.data.network.api.models.UserResponse
import io.reactivex.Observable
import retrofit2.Response
import ru.school21.eleonard.data.network.api.models.UserInListResponse
import ru.school21.eleonard.data.network.helpers.ResponseWrapper

interface IUsersDao {
	@GET("/v2/users/{user}")
	suspend fun getUserInfo(
		@Header("Authorization") Authorization: String = TokenRepository.accessToken,
		@Path("user") user: String
	): Response<UserResponse>

	@GET("/v2/users")
	suspend fun getAllUsers(
		@Header("Authorization") Authorization: String = TokenRepository.accessToken,
	): Response<List<UserInListResponse>>
}