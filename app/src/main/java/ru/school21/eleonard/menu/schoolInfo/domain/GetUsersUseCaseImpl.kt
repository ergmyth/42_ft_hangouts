package ru.school21.eleonard.menu.schoolInfo.domain

import kotlinx.coroutines.flow.Flow
import ru.school21.eleonard.data.network.api.models.UserInListResponse
import ru.school21.eleonard.data.network.api.models.UserResponse
import ru.school21.eleonard.data.network.helpers.Event
import ru.school21.eleonard.menu.schoolInfo.data.SchoolInfoRepository
import javax.inject.Inject

class GetUsersUseCaseImpl @Inject constructor(
	private val schoolInfoRepository: SchoolInfoRepository,
): GetUsersUseCase {
	override fun invoke(): Flow<Event<List<UserInListResponse>>> {
		return schoolInfoRepository.getUsers()
	}
}