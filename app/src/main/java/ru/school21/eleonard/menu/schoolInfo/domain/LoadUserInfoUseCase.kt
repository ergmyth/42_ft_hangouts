package ru.school21.eleonard.menu.schoolInfo.domain

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import ru.school21.eleonard.data.api.models.UserResponse
import ru.school21.eleonard.helpers.requests.Event

interface LoadUserInfoUseCase {
	fun invoke(userName: String, userInfoLoadingResponse: MutableLiveData<Event<UserResponse>>)
}