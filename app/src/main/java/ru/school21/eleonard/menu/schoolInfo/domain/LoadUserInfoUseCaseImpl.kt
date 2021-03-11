package ru.school21.eleonard.menu.schoolInfo.domain

import androidx.lifecycle.MutableLiveData
import ru.school21.eleonard.data.network.api.models.UserResponse
import ru.school21.eleonard.data.network.helpers.Event
import ru.school21.eleonard.menu.schoolInfo.data.SchoolInfoRepository
import javax.inject.Inject

class LoadUserInfoUseCaseImpl @Inject constructor(
	private val schoolInfoRepository: SchoolInfoRepository
) : LoadUserInfoUseCase {
	override fun invoke(userName: String, userInfoLoadingResponse: MutableLiveData<Event<UserResponse>>) {
		schoolInfoRepository.loadUserInfo(userName, userInfoLoadingResponse)
	}
}