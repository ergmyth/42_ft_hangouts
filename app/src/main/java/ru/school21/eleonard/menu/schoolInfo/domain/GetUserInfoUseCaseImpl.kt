package ru.school21.eleonard.menu.schoolInfo.domain

import kotlinx.coroutines.flow.Flow
import ru.school21.eleonard.data.network.api.models.UserResponse
import ru.school21.eleonard.data.network.helpers.Event
import ru.school21.eleonard.menu.schoolInfo.data.SchoolInfoRepository
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject constructor(
	private val schoolInfoRepository: SchoolInfoRepository
) : GetUserInfoUseCase {
	override fun invoke(userName: String): Flow<Event<UserResponse>> {
		return schoolInfoRepository.getUser(userName)
	}
}