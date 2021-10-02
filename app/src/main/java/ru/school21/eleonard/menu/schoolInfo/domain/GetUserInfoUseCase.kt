package ru.school21.eleonard.menu.schoolInfo.domain

import kotlinx.coroutines.flow.Flow
import ru.school21.eleonard.data.network.api.models.UserResponse
import ru.school21.eleonard.data.network.helpers.Event

interface GetUserInfoUseCase {
	operator fun invoke(userName: String): Flow<Event<UserResponse>>
}