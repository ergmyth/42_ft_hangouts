package ru.school21.eleonard.data.network

import ru.school21.eleonard.data.network.api.IUsersDao
import ru.school21.eleonard.data.network.api.models.UserResponse
import javax.inject.Inject
import io.reactivex.Observable
import retrofit2.Response
import ru.school21.eleonard.data.network.api.models.UserInListResponse
import ru.school21.eleonard.data.network.helpers.ResponseWrapper


class ApiRepository @Inject constructor(
	val usersDao: IUsersDao,
) {
	suspend fun getUserInfo(userName: String): Response<UserResponse> {
		return usersDao.getUserInfo(user = userName)
	}

	suspend fun getAllUsers(): Response<List<UserInListResponse>> {
		return usersDao.getAllUsers()
	}
}