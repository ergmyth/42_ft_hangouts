package ru.school21.eleonard.menu.schoolInfo.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.school21.eleonard.data.network.helpers.Event
import ru.school21.eleonard.data.network.api.models.UserResponse
import ru.school21.eleonard.menu.schoolInfo.domain.LoadUserInfoUseCase

class SchoolInfoViewModel @ViewModelInject constructor(
    private val loadUserInfoUseCase: LoadUserInfoUseCase
) : ViewModel() {
    val userInfoLoadingResponse = MutableLiveData<Event<UserResponse>>()
    fun loadUserInfo(userName: String) {
        loadUserInfoUseCase.invoke(userName, userInfoLoadingResponse)
    }
}