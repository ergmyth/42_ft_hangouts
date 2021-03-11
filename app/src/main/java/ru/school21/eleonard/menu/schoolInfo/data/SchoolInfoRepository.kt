package ru.school21.eleonard.menu.schoolInfo.data

import androidx.lifecycle.MutableLiveData
import ru.school21.eleonard.data.network.api.models.UserResponse
import ru.school21.eleonard.data.network.helpers.Event

interface SchoolInfoRepository {
	fun loadUserInfo(userName: String, userInfoLoadingResponse: MutableLiveData<Event<UserResponse>>)
}