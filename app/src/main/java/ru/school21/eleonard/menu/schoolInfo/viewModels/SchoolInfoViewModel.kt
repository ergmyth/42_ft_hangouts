package ru.school21.eleonard.menu.schoolInfo.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import ru.school21.eleonard.data.network.api.models.UserInListResponse
import ru.school21.eleonard.data.network.helpers.Event
import ru.school21.eleonard.data.network.api.models.UserResponse
import ru.school21.eleonard.menu.schoolInfo.domain.GetUserInfoUseCase
import ru.school21.eleonard.menu.schoolInfo.domain.GetUsersUseCase

class SchoolInfoViewModel @ViewModelInject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUsersUseCase: GetUsersUseCase,
) : ViewModel() {

    val userInfoGettingResponse = MutableLiveData<Event<UserResponse>>()
    fun getUserInfo(userName: String) {
        viewModelScope.launch {
            getUserInfoUseCase(userName).collect {
                userInfoGettingResponse.postValue(it)
            }
        }
    }

    val usersGettingResponse = MutableLiveData<Event<List<UserInListResponse>>>()
    fun getAllUsers() {
        viewModelScope.launch {
            getUsersUseCase().collect {
                usersGettingResponse.postValue(it)
            }
        }
    }
}