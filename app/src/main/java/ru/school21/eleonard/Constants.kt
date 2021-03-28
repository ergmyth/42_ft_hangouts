package ru.school21.eleonard

object Constants {
	const val SP_BROADCAST_ACTION_MESSAGE = "SP_BROADCAST_ACTION_MESSAGE"
	const val SP_BROADCAST_ACTION_MESSAGE_TO_CHAT = "SP_BROADCAST_ACTION_MESSAGE_TO_CHAT"
	const val SP_CHOSEN_THEME = "SP_CHOSEN_THEME"
	const val SP_ACCESS_TOKEN = "SP_ACCESS_TOKEN"
	const val SP_PIN_ENCODED = "SP_PIN_ENCODED"

	const val UID = "87a5927117915b1ebe7d1f0290d09a502cb1b7831d3c73ddcd59a7fc90f0f4bf"
	const val SECRET_ID = "aeecab89cfe7456ffe8bd2c1299c6945b1f72069f6e75023f012a14581342862"
	const val GRANT_TYPE = "client_credentials"
	const val BASE_URL = "https://api.intra.42.fr"
	const val TOKEN_PREFIX = "Bearer "
	const val MAX_REQUESTS = 2
	const val CONNECT_TIMEOUT_SECONDS = 60L
	const val READ_TIMEOUT_SECONDS = 60L
	const val WRITE_TIMEOUT_SECONDS = 60L

	const val HTTP_SUCCESSFUL = 200
	const val HTTP_BAD_REQUEST = 400
	const val HTTP_UNAUTHORIZED = 401
	const val HTTP_FORBIDDEN = 403
	const val HTTP_NOT_FOUND = 404
	const val HTTP_INTERNAL_SERVER_ERROR = 500
}