package ru.school21.eleonard.data.network

import ru.school21.eleonard.data.network.api.IUsersApi
import ru.school21.eleonard.data.network.api.models.UserResponse
import javax.inject.Inject
import io.reactivex.Observable
import ru.school21.eleonard.data.network.helpers.ResponseWrapper


class ApiRepository @Inject constructor(
	val usersApi: IUsersApi,
) {
	fun getUserInfo(userName: String): Observable<ResponseWrapper<UserResponse>> {
		return usersApi.getUserInfo(user = userName)
	}
}