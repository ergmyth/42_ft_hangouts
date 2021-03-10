package ru.school21.eleonard.data

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.school21.eleonard.data.api.IAuthApi
import ru.school21.eleonard.data.api.IUsersApi
import java.util.concurrent.TimeUnit

object NetworkHolder {
	private const val baseUrl = "https://api.intra.42.fr"

	var retrofitClient: Retrofit
	var authenticatorRetrofitClient: Retrofit
	var apiRepository: ApiRepository
	var httpClient: OkHttpClient

	init {
		authenticatorRetrofitClient = Retrofit.Builder()
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(baseUrl)
			.build()

		val interceptor = HttpLoggingInterceptor()
		interceptor.level = HttpLoggingInterceptor.Level.BODY

		val dispatcher = Dispatcher()
		dispatcher.maxRequests = 2

		httpClient = OkHttpClient().newBuilder()
			.followRedirects(true)
			.followSslRedirects(false)
			.addInterceptor(interceptor)
			.authenticator(ApiAuthenticator())
			.dispatcher(dispatcher)
			.connectTimeout(60, TimeUnit.SECONDS)
			.readTimeout(60, TimeUnit.SECONDS)
			.writeTimeout(60, TimeUnit.SECONDS)
			.build()

		retrofitClient = Retrofit.Builder()
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(baseUrl)
			.client(httpClient)
			.build()

		apiRepository = ApiRepository(
			retrofitClient.create(IUsersApi::class.java),
			retrofitClient.create(IAuthApi::class.java),
		)
	}
}