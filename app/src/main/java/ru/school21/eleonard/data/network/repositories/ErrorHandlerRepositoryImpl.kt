package ru.school21.eleonard.data.network.repositories

import android.util.Log
import dagger.hilt.android.qualifiers.ActivityContext
import ru.school21.eleonard.Constants
import javax.inject.Inject

class ErrorHandlerRepositoryImpl : ErrorHandlerRepository {
	override fun handleHttpException(tag: String, httpExceptionCode: Int): String {
		val errorMessage = when (httpExceptionCode) {
			Constants.HTTP_NOT_FOUND -> "Отсутствует подключение к интернету. Попробуйте повторить позже."
			Constants.HTTP_FORBIDDEN -> "Произошла ошибка. Не удалось загрузить необходимые данные."
			Constants.HTTP_BAD_REQUEST -> "Не удалось отправить запрос. Попробуйте повторить позже."
			else -> "При отправке запроса произошла ошибка. Попробуйте повторить позже."
		}
		Log.d(tag, errorMessage)
		return errorMessage
	}

	override fun handleErrorMessage(tag: String, errorMessage: String): String {
		Log.d(tag, errorMessage)
		return "При отправке запроса произошла ошибка. Попробуйте повторить позже."
	}
}