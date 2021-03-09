package ru.school21.eleonard.data

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent
import ru.school21.eleonard.BaseApp
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.io.IOException

class ApiAuthenticator : Authenticator {
	@EntryPoint
	@InstallIn(ApplicationComponent::class)
	interface ApiAuthenticatorEntryPoint {
		fun provideContext(): Context
	}

	var context: Context

	init {
		val appContext = BaseApp.getInstance().applicationContext ?: throw IllegalStateException()
		val hiltEntryPoint =
			EntryPointAccessors.fromApplication(appContext, ApiAuthenticatorEntryPoint::class.java)

		context = hiltEntryPoint.provideContext()
	}

	//todo запилить запрос и обработку просрочки токена. Если ошибка при обновлении токена - реавторизация.
	@Throws(IOException::class)
	override fun authenticate(route: Route?, response: Response): Request? {
		return null
	}
}