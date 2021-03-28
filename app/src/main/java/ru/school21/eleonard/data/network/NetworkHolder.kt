package ru.school21.eleonard.data.network

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.school21.eleonard.data.network.api.IUsersApi
import ru.school21.eleonard.Constants
import ru.school21.eleonard.data.network.helpers.NetworkUtils
import java.util.concurrent.TimeUnit

object NetworkHolder {

	var retrofitClient: Retrofit
	var authenticatorRetrofitClient: Retrofit
	var apiRepository: ApiRepository
	var httpClient: OkHttpClient

	init {
		val httpLoggingInterceptor = HttpLoggingInterceptor()
		httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

		val dispatcher = Dispatcher()
		dispatcher.maxRequests = Constants.MAX_REQUESTS

		authenticatorRetrofitClient = Retrofit.Builder()
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(Constants.BASE_URL)
			.build()

		httpClient = OkHttpClient().newBuilder()
			.followRedirects(true)
			.followSslRedirects(false)
			.addInterceptor(httpLoggingInterceptor)
			.addInterceptor(ConnectivityInterceptor())
			.authenticator(ApiAuthenticator())
			.dispatcher(dispatcher)
			.connectTimeout(Constants.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
			.readTimeout(Constants.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
			.writeTimeout(Constants.WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
			.build()

		retrofitClient = Retrofit.Builder()
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(Constants.BASE_URL)
			.client(httpClient)
			.build()

		apiRepository = ApiRepository(
			retrofitClient.create(IUsersApi::class.java),
		)
	}
}