package ru.school21.eleonard.helpers.requests.data

interface ErrorHandlerRepository {
	fun handleErrorMessage(errorMessage: String): String
}