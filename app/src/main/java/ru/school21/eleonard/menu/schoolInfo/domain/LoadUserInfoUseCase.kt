package ru.school21.eleonard.menu.schoolInfo.domain

import androidx.lifecycle.MutableLiveData
import ru.school21.eleonard.data.network.api.models.UserResponse
import ru.school21.eleonard.data.network.helpers.Event

interface LoadUserInfoUseCase {
	fun invoke(userName: String, userInfoLoadingResponse: MutableLiveData<Event<UserResponse>>)
}