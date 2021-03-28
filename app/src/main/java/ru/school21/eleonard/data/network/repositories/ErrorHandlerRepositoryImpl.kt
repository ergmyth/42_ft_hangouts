package ru.school21.eleonard.data.network.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.HttpException
import ru.school21.eleonard.BaseApp
import ru.school21.eleonard.Constants
import ru.school21.eleonard.R
import ru.school21.eleonard.data.network.helpers.ApiErrors
import ru.school21.eleonard.data.network.helpers.Event

class ErrorHandlerRepositoryImpl : ErrorHandlerRepository {
	override fun <T> handleHttpException(functionName: String?, httpExceptionCode: Int, liveData: MutableLiveData<Event<T>>) {
		val errorMessage = when (httpExceptionCode) {
			Constants.HTTP_NOT_FOUND -> BaseApp.getInstance().resources.getString(R.string.server_404)
			Constants.HTTP_FORBIDDEN -> BaseApp.getInstance().resources.getString(R.string.server_403)
			Constants.HTTP_BAD_REQUEST -> BaseApp.getInstance().resources.getString(R.string.server_400)
			else -> BaseApp.getInstance().resources.getString(R.string.server_500)
		}
		Log.d(functionName, errorMessage)
		liveData.value = Event.error(errorMessage)
	}

	override fun <T> handleErrorMessage(functionName: String?, errorMessage: String, liveData: MutableLiveData<Event<T>>) {
		Log.d(functionName, errorMessage)
		liveData.value = if (errorMessage == BaseApp.getInstance().resources.getString(R.string.network_connection_error))
			Event.error(errorMessage)
		else
			Event.error(BaseApp.getInstance().resources.getString(R.string.default_error_message))
	}

	override fun <T> handleApiError(functionName: String?, apiError: ApiErrors, liveData: MutableLiveData<Event<T>>) {
		val errorMessage = when (apiError) {
			ApiErrors.IS_NOT_200 -> BaseApp.getInstance().resources.getString(R.string.api_not_200)
			ApiErrors.EMPTY_DATA -> BaseApp.getInstance().resources.getString(R.string.api_empty_data)
			ApiErrors.EMPTY_ERROR -> BaseApp.getInstance().resources.getString(R.string.api_empty_message)
		}
		Log.d(functionName, errorMessage)
		liveData.value = Event.error(errorMessage)
	}

	override fun <T> handleErrorResponse(e: Throwable, functionName: String?, liveData: MutableLiveData<Event<T>>) {
		(e as? HttpException)?.let {
			handleHttpException(functionName, it.code(), liveData)
		} ?: if (e.message == null)
			handleApiError(functionName, ApiErrors.EMPTY_ERROR, liveData)
		else
			handleErrorMessage(functionName, e.message!!, liveData)
	}
}