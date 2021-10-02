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
	override fun handleHttpException(httpExceptionCode: Int): String {
		return when (httpExceptionCode) {
			Constants.HTTP_NOT_FOUND -> BaseApp.getInstance().resources.getString(R.string.server_404)
			Constants.HTTP_FORBIDDEN -> BaseApp.getInstance().resources.getString(R.string.server_403)
			Constants.HTTP_BAD_REQUEST -> BaseApp.getInstance().resources.getString(R.string.server_400)
			else -> BaseApp.getInstance().resources.getString(R.string.server_500)
		}
	}

	override fun handleErrorMessage(errorMessage: String): String {
		return if (errorMessage == BaseApp.getInstance().resources.getString(R.string.network_connection_error))
			errorMessage
		else
			BaseApp.getInstance().resources.getString(R.string.default_error_message)
	}

	override fun handleApiError(apiError: ApiErrors): String {
		return when (apiError) {
			ApiErrors.IS_NOT_200 -> BaseApp.getInstance().resources.getString(R.string.api_not_200)
			ApiErrors.EMPTY_DATA -> BaseApp.getInstance().resources.getString(R.string.api_empty_data)
			ApiErrors.EMPTY_ERROR -> BaseApp.getInstance().resources.getString(R.string.api_empty_message)
		}
	}

	override fun handleErrorResponse(e: Throwable) {
		(e as? HttpException)?.let {
			handleHttpException(it.code())
		} ?: if (e.message == null)
			handleApiError(ApiErrors.EMPTY_ERROR)
		else
			handleErrorMessage(e.message!!)
	}
}