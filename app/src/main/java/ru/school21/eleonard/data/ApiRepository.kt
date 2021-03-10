package ru.school21.eleonard.data

import ru.school21.eleonard.data.api.IAuthApi
import ru.school21.eleonard.data.api.IUsersApi
import ru.school21.eleonard.data.api.models.UserResponse
import javax.inject.Inject
import io.reactivex.Observable
import retrofit2.Response


class ApiRepository @Inject constructor(
	val usersApi: IUsersApi,
	val authApi: IAuthApi,
) {
	fun getUserInfo(userName: String): Observable<UserResponse> {
		return usersApi.getUserInfo(user = userName)
	}
}