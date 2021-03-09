package ru.school21.eleonard.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.school21.eleonard.data.ApiRepository
import ru.school21.eleonard.data.NetworkHolder
import ru.school21.eleonard.data.api.IContactsApi
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
		dispatcher.maxRequests = 1

		return OkHttpClient().newBuilder()
			.followRedirects(true)
			.followSslRedirects(false)
			.addInterceptor(interceptor)
			.dispatcher(dispatcher)
			.connectTimeout(60, TimeUnit.SECONDS)
			.readTimeout(60, TimeUnit.SECONDS)
			.writeTimeout(60, TimeUnit.SECONDS)
			.build()
	}

	@Provides
	@Singleton
	fun provideRetrofitClient(): Retrofit {
		return NetworkHolder.retrofitClient
	}

	@Singleton
	@Provides
	fun provideContext(@ApplicationContext context: Context): Context {
		return context
	}

	@Singleton
	@Provides
	fun provideApiRepository(): ApiRepository {
		return NetworkHolder.apiRepository
	}

	@Provides
	fun provideCommonApi(retrofit: Retrofit): IContactsApi {
		return retrofit.create(IContactsApi::class.java)
	}
}