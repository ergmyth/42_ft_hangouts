package ru.school21.eleonard.data.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.school21.eleonard.data.network.ApiRepository
import ru.school21.eleonard.data.network.NetworkHolder
import ru.school21.eleonard.data.network.api.IAuthApi
import ru.school21.eleonard.data.network.api.IUsersApi
import ru.school21.eleonard.Constants
import ru.school21.eleonard.data.network.repositories.CompositeDisposableRepository
import ru.school21.eleonard.data.network.repositories.CompositeDisposableRepositoryImpl
import ru.school21.eleonard.data.network.repositories.ErrorHandlerRepository
import ru.school21.eleonard.data.network.repositories.ErrorHandlerRepositoryImpl
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
	@Provides
	@Singleton
	fun provideHttpClient(): OkHttpClient {
		val interceptor = HttpLoggingInterceptor()
		interceptor.level = HttpLoggingInterceptor.Level.BODY

		val dispatcher = Dispatcher()
		dispatcher.maxRequests = Constants.MAX_REQUESTS

		return OkHttpClient().newBuilder()
			.followRedirects(true)
			.followSslRedirects(false)
			.addInterceptor(interceptor)
			.dispatcher(dispatcher)
			.connectTimeout(Constants.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
			.readTimeout(Constants.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
			.writeTimeout(Constants.WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
			.build()
	}

	@Provides
	@Singleton
	fun provideRetrofitClient(): Retrofit {
		return NetworkHolder.retrofitClient
	}

	@Singleton
	@Provides
	fun provideApiRepository(): ApiRepository {
		return NetworkHolder.apiRepository
	}

	@Provides
	fun provideUserApi(retrofit: Retrofit): IUsersApi {
		return retrofit.create(IUsersApi::class.java)
	}

	@Provides
	fun provideAuthApi(retrofit: Retrofit): IAuthApi {
		return retrofit.create(IAuthApi::class.java)
	}

	@Singleton
	@Provides
	fun provideErrorHandlerRepository(): ErrorHandlerRepository {
		return ErrorHandlerRepositoryImpl()
	}

	@Provides
	fun provideCompositeDisposableRepository(): CompositeDisposableRepository {
		return CompositeDisposableRepositoryImpl()
	}
}