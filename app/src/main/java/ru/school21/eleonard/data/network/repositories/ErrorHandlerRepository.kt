package ru.school21.eleonard.data.network.repositories

import androidx.lifecycle.MutableLiveData
import ru.school21.eleonard.data.network.helpers.ApiErrors
import ru.school21.eleonard.data.network.helpers.Event


interface ErrorHandlerRepository {
	fun <T> handleHttpException(functionName: String?, httpExceptionCode: Int, liveData: MutableLiveData<Event<T>>)

	fun <T> handleErrorMessage(functionName: String?, errorMessage: String, liveData: MutableLiveData<Event<T>>)

	fun <T> handleApiError(functionName: String?, apiError: ApiErrors, liveData: MutableLiveData<Event<T>>)

	fun <T> handleErrorResponse(e: Throwable, functionName: String?, liveData: MutableLiveData<Event<T>>)
}