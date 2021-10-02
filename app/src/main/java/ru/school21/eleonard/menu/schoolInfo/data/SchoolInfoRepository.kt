package ru.school21.eleonard.menu.schoolInfo.data

import kotlinx.coroutines.flow.Flow
import ru.school21.eleonard.data.network.api.models.UserInListResponse
import ru.school21.eleonard.data.network.api.models.UserResponse
import ru.school21.eleonard.data.network.helpers.Event

interface SchoolInfoRepository {
	fun getUser(userName: String): Flow<Event<UserResponse>>
	fun getUsers(): Flow<Event<List<UserInListResponse>>>
}