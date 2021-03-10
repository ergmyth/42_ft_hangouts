package ru.school21.eleonard.data.api

import retrofit2.http.*
import ru.school21.eleonard.data.TokenRepository
import ru.school21.eleonard.data.api.models.UserResponse
import io.reactivex.Observable
import retrofit2.Response

interface IUsersApi {
	@GET("/v2/users/{user}")
	fun getUserInfo(
		@Header("Authorization") Authorization: String = "Bearer " + TokenRepository.accessToken,
		@Path("user") user: String
	): Observable<UserResponse>
}