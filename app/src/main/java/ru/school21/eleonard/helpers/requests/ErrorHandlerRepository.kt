package ru.school21.eleonard.helpers.requests

interface ErrorHandlerRepository {
	fun handleErrorMessage(errorMessage: String): String
}