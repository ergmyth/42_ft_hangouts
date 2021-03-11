package ru.school21.eleonard.data.network.repositories

interface ErrorHandlerRepository {
	fun handleHttpException(tag: String, httpExceptionCode: Int): String
	fun handleErrorMessage(tag: String, errorMessage: String): String
}