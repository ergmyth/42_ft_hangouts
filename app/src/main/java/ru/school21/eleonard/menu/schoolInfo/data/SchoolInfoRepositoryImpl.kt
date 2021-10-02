package ru.school21.eleonard.menu.schoolInfo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.school21.eleonard.data.network.helpers.Event
import javax.inject.Inject
import ru.school21.eleonard.data.network.ApiRepository
import ru.school21.eleonard.data.network.api.models.UserResponse
import ru.school21.eleonard.data.network.repositories.ErrorHandlerRepository

class SchoolInfoRepositoryImpl @Inject constructor(
	private val apiRepository: ApiRepository,
	private val errorHandlerRepository: ErrorHandlerRepository,
) : SchoolInfoRepository {
	override fun getUser(userName: String) = flow {
		emit(Event.loading())

		val apiResponse = apiRepository.getUserInfo(userName)

		if (apiResponse.isSuccessful && apiResponse.body() != null) {
			emit(Event.success(apiResponse.body()))
			return@flow
		}
		emit(Event.error(errorHandlerRepository.handleErrorMessage("")))
	}.catch { e ->
		emit(Event.error("Ошибка при загрузке getUser: ${e.message}\n"))
	}

	override fun getUsers() = flow {
		emit(Event.loading())

		val apiResponse = apiRepository.getAllUsers()

		if (apiResponse.isSuccessful && apiResponse.body() != null) {
			emit(Event.success(apiResponse.body()))
			return@flow
		}
		emit(Event.error(errorHandlerRepository.handleErrorMessage("")))
	}.catch { e ->
		emit(Event.error("Ошибка при загрузке getUsers: ${e.message}\n"))
	}
}