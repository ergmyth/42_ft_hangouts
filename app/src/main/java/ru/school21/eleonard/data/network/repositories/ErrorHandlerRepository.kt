package ru.school21.eleonard.data.network.repositories

import androidx.lifecycle.MutableLiveData
import ru.school21.eleonard.data.network.helpers.ApiErrors
import ru.school21.eleonard.data.network.helpers.Event


interface ErrorHandlerRepository {
	fun handleHttpException(httpExceptionCode: Int): String

	fun handleErrorMessage(errorMessage: String): String

	fun handleApiError(apiError: ApiErrors): String

	fun handleErrorResponse(e: Throwable)
}